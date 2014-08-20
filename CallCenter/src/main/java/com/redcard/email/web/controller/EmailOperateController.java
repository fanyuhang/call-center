package com.redcard.email.web.controller;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.email.entity.EmailOperate;
import com.redcard.email.service.EmailOperateManager;

@Controller
@RequestMapping(value = "/email/operate")
public class EmailOperateController {

	// private static Logger log =
	// LoggerFactory.getLogger(MessageOperateController.class);

	@Autowired
	private EmailOperateManager emailOperateManager;

	@Value("#{settingsMap['emailSendAccount']}")
	private String emailSendAccount;

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "email/operate/list";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public DataResponse<EmailOperate> list(GridPageRequest pageRequest, String where) {
		pageRequest.setSort("fldOperateDate", "desc");
		return (new DataResponse<EmailOperate>(emailOperateManager.queryEmailOperates(pageRequest, where)));
	}

	@RequestMapping(value = "add")
	public String add(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("emailSendAccount", emailSendAccount);
		return "email/operate/add";
	}

	/**
	 * 发送邮件
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public AsyncResponse save(EmailOperate emailOperate) {
		AsyncResponse result = new AsyncResponse(false, "邮件发送成功！");
		String senderEmail = emailOperate.getFldSenderEmail();
		String[] receiveEmails = emailOperate.getFldReceiverEmails().split(";");
		String fldTitle = emailOperate.getFldTitle();
		String fldContent = emailOperate.getFldContent();
		// 发送邮件
		if (ArrayUtils.isNotEmpty(receiveEmails)) {
			emailOperateManager.sendEmails(fldTitle, senderEmail, receiveEmails, fldContent);
		}
		emailOperate.setFldSendDate(new Date());
		emailOperate.setFldId(EntityUtil.getId());
		emailOperate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailOperate.setFldCreateDate(new Date());
		emailOperate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailOperate.setFldOperateDate(new Date());
		if (ArrayUtils.isNotEmpty(receiveEmails)) {
			emailOperate.setFldEmailNum(receiveEmails.length);
		}
		emailOperateManager.save(emailOperate);
		return result;
	}

	@RequestMapping(value = "view")
	public String view(String menuNo, String fldId, Model model) {
		EmailOperate emailOperate = emailOperateManager.find(fldId);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("emailOperate", emailOperate);
		return "email/operate/view";
	}

}