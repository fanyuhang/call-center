package com.redcard.email.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
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
		emailTemplate.setFldStatus(Constant.EMAIL_TEMPLATE_STATUS_NORMAL);
		emailTemplate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailTemplate.setFldCreateDate(new Date());
		emailTemplate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
		emailTemplate.setFldOperateDate(new Date());
		emailTemplateManager.save(emailTemplate);
		return result;
	}

	// /**
	// * 查询短信模板记录
	// */
	// @RequestMapping(value = "list")
	// @ResponseBody
	// public DataResponse<MessageTemplate> list(GridPageRequest pageRequest,
	// String where) {
	// pageRequest.setSort("fldOperateDate", "desc");
	// return (new
	// DataResponse<MessageTemplate>(messageTemplateManager.queryMessageTemplates(pageRequest,
	// where)));
	// }
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
	// /**
	// * 从短信模板查询页面点击新增模板跳转到新增短信模板页面
	// */
	// @RequestMapping(value = "add")
	// public String add(String menuNo, Model model) {
	// model.addAttribute("menuNo", menuNo);
	// return "message/template/add";
	// }
	//
	// /**
	// * 新增短信模板页面的保存按钮
	// */
	// @RequestMapping(value = "save")
	// @ResponseBody
	// public AsyncResponse save(MessageTemplate messageTemplate) {
	// AsyncResponse result = new AsyncResponse(false, "保存短信模板成功！");
	// if (messageTemplateManager.isExistMessageTemplate(messageTemplate)) {
	// return new AsyncResponse(true, "该短信模板名称已被占用，请更换模板名称！");
	// }
	// messageTemplate.setFldId(EntityUtil.getId());
	// messageTemplate.setFldStatus(Constant.MESSAGE_TEMPLATE_STATUS_NORMAL);
	// messageTemplate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
	// messageTemplate.setFldCreateDate(new Date());
	// messageTemplate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
	// messageTemplate.setFldOperateDate(new Date());
	// messageTemplateManager.save(messageTemplate);
	// return result;
	// }
	//
	// @RequestMapping(value = "view")
	// public String view(String menuNo, String fldId, Model model) {
	// MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
	// model.addAttribute("menuNo", menuNo);
	// model.addAttribute("messageTemplate", messageTemplate);
	// return "message/template/view";
	// }
	//
	// @RequestMapping(value = "edit")
	// public String edit(String menuNo, String fldId, Model model) {
	// MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
	// model.addAttribute("menuNo", menuNo);
	// model.addAttribute("messageTemplate", messageTemplate);
	// return "message/template/edit";
	// }
	//
	// @RequestMapping(value = "update")
	// @ResponseBody
	// public AsyncResponse update(MessageTemplate messageTemplate) {
	// AsyncResponse result = new AsyncResponse(false, "修改短信模板成功！");
	// messageTemplate.setFldOperateDate(new Date());
	// MessageTemplate originalMessageTemplate =
	// messageTemplateManager.find(messageTemplate.getFldId());
	// messageTemplate.setFldCreateUserNo(originalMessageTemplate.getFldCreateUserNo());
	// messageTemplate.setFldCreateDate(originalMessageTemplate.getFldCreateDate());
	// messageTemplateManager.save(messageTemplate);
	// return result;
	// }
	//
	// @RequestMapping(value = "delete")
	// @ResponseBody
	// public AsyncResponse delete(String fldId) {
	// AsyncResponse result = new AsyncResponse(false, "删除短信模板成功！");
	// MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
	// messageTemplate.setFldOperateDate(new Date());
	// messageTemplate.setFldStatus(Constant.MESSAGE_TEMPLATE_STATUS_DIABLED);
	// messageTemplateManager.save(messageTemplate);
	// return result;
	// }
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