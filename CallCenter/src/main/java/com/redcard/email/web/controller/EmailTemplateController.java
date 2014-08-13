package com.redcard.email.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.email.entity.EmailTemplate;
import com.redcard.email.service.EmailTemplateManager;

@Controller
@RequestMapping(value = "/email/template")
public class EmailTemplateController {

	// private static Logger log =
	// LoggerFactory.getLogger(MessageTemplateController.class);

	@Autowired
	private EmailTemplateManager emailTemplateManager;

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "email/template/list";
	}

	/**
	 * 新增邮件模板页面的保存按钮
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public AsyncResponse save(EmailTemplate emailTemplate) {
		AsyncResponse result = new AsyncResponse(false, "保存邮件模板成功！");
		if (emailTemplateManager.isExistEmailTemplate(emailTemplate)) {
			return new AsyncResponse(true, "该邮件模板名称已被占用，请更换模板名称！");
		}
		emailTemplate.setFldId(EntityUtil.getId());
		emailTemplate.setFldStatus(Constant.EMAIL_TEMPLATE_STATUS_NORMAL);
		emailTemplate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailTemplate.setFldCreateDate(new Date());
		emailTemplate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailTemplate.setFldOperateDate(new Date());
		emailTemplateManager.save(emailTemplate);
		return result;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public DataResponse<EmailTemplate> list(GridPageRequest pageRequest, String where) {
		pageRequest.setSort("fldOperateDate", "desc");
		return (new DataResponse<EmailTemplate>(emailTemplateManager.queryEmailTemplates(pageRequest, where)));
	}

	//
	// @RequestMapping(value = "listAll")
	// @ResponseBody
	// public AsyncResponse listAll() {
	// AsyncResponse response = new AsyncResponse(false, "加载成功");
	// response.setData(messageTemplateManager.findAll());
	// return response;
	// }
	//
	// @RequestMapping(value = "conditionalList")
	// @ResponseBody
	// public DataResponse<MessageTemplate> conditionalList(GridPageRequest
	// pageRequest, String where) {
	// pageRequest.setSort("fldOperateDate", "desc");
	// return (new
	// DataResponse<MessageTemplate>(messageTemplateManager.queryMessageTemplates(pageRequest,
	// where)));
	// }
	//

	@RequestMapping(value = "add")
	public String add(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "email/template/add";
	}

	@RequestMapping(value = "view")
	public String view(String menuNo, String fldId, Model model) {
		EmailTemplate emailTemplate = emailTemplateManager.find(fldId);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("emailTemplate", emailTemplate);
		return "email/template/view";
	}

	@RequestMapping(value = "edit")
	public String edit(String menuNo, String fldId, Model model) {
		EmailTemplate emailTemplate = emailTemplateManager.find(fldId);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("emailTemplate", emailTemplate);
		return "email/template/edit";
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public AsyncResponse update(EmailTemplate emailTemplate) {
		AsyncResponse result = new AsyncResponse(false, "修改邮件模板成功！");
		EmailTemplate originalEmailTemplate = emailTemplateManager.find(emailTemplate.getFldId());
		emailTemplate.setFldOperateDate(new Date());
		emailTemplate.setFldCreateUserNo(originalEmailTemplate.getFldCreateUserNo());
		emailTemplate.setFldCreateDate(originalEmailTemplate.getFldCreateDate());
		emailTemplateManager.save(emailTemplate);
		return result;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public AsyncResponse delete(String fldId) {
		AsyncResponse result = new AsyncResponse(false, "删除邮件模板成功！");
		EmailTemplate emailTemplate = emailTemplateManager.find(fldId);
		emailTemplate.setFldOperateDate(new Date());
		emailTemplate.setFldStatus(Constant.EMAIL_TEMPLATE_STATUS_DIABLED);
		emailTemplateManager.save(emailTemplate);
		return result;
	}
	//
	// @RequestMapping(value = "findMessageTemplateDetail")
	// @ResponseBody
	// public AsyncResponse findMessageTemplateDetail(String fldId) {
	// AsyncResponse response = new AsyncResponse();
	// List<MessageTemplate> list = new ArrayList<MessageTemplate>();
	// MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
	// list.add(messageTemplate);
	// response.addData(list);
	// response.setIsError(false);
	// return response;
	// }
}