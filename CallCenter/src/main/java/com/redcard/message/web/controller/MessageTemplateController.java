package com.redcard.message.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.redcard.message.entity.MessageTemplate;
import com.redcard.message.service.MessageTemplateManager;

@Controller
@RequestMapping(value = "/message/template")
public class MessageTemplateController {

	// private static Logger log =
	// LoggerFactory.getLogger(MessageTemplateController.class);

	@Autowired
	private MessageTemplateManager messageTemplateManager;

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/template/list";
	}

	/**
	 * 查询短信模板记录
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public DataResponse<MessageTemplate> list(GridPageRequest pageRequest, String where) {
		pageRequest.setSort("fldOperateDate", "desc");
		return (new DataResponse<MessageTemplate>(messageTemplateManager.queryMessageTemplates(pageRequest, where)));
	}

	@RequestMapping(value = "listAll")
	@ResponseBody
	public AsyncResponse listAll() {
		AsyncResponse response = new AsyncResponse(false, "加载成功");
		response.setData(messageTemplateManager.findAll());
		return response;
	}

	@RequestMapping(value = "conditionalList")
	@ResponseBody
	public DataResponse<MessageTemplate> conditionalList(GridPageRequest pageRequest, String where) {
		pageRequest.setSort("fldOperateDate", "desc");
		return (new DataResponse<MessageTemplate>(messageTemplateManager.queryMessageTemplates(pageRequest, where)));
	}

	/**
	 * 从短信模板查询页面点击新增模板跳转到新增短信模板页面
	 */
	@RequestMapping(value = "add")
	public String add(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/template/add";
	}

	/**
	 * 新增短信模板页面的保存按钮
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public AsyncResponse save(MessageTemplate messageTemplate) {
		AsyncResponse result = new AsyncResponse(false, "保存短信模板成功！");
		if (messageTemplateManager.isExistMessageTemplate(messageTemplate)) {
			return new AsyncResponse(true, "该短信模板名称已被占用，请更换模板名称！");
		}
		messageTemplate.setFldId(EntityUtil.getId());
		messageTemplate.setFldStatus(Constant.MESSAGE_TEMPLATE_STATUS_NORMAL);
		messageTemplate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
		messageTemplate.setFldCreateDate(new Date());
		messageTemplate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
		messageTemplate.setFldOperateDate(new Date());
		messageTemplateManager.save(messageTemplate);
		return result;
	}

	@RequestMapping(value = "view")
	public String view(String menuNo, String fldId, Model model) {
		MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("messageTemplate", messageTemplate);
		return "message/template/view";
	}

	@RequestMapping(value = "edit")
	public String edit(String menuNo, String fldId, Model model) {
		MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("messageTemplate", messageTemplate);
		return "message/template/edit";
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public AsyncResponse update(MessageTemplate messageTemplate) {
		AsyncResponse result = new AsyncResponse(false, "修改短信模板成功！");
		MessageTemplate originalMessageTemplate = messageTemplateManager.find(messageTemplate.getFldId());
		messageTemplate.setFldCreateUserNo(originalMessageTemplate.getFldCreateUserNo());
		messageTemplate.setFldOperateDate(new Date());
		messageTemplate.setFldCreateDate(originalMessageTemplate.getFldCreateDate());
		messageTemplateManager.save(messageTemplate);
		return result;
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public AsyncResponse delete(String fldId) {
		AsyncResponse result = new AsyncResponse(false, "删除短信模板成功！");
		MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
		messageTemplate.setFldOperateDate(new Date());
		messageTemplate.setFldStatus(Constant.MESSAGE_TEMPLATE_STATUS_DIABLED);
		messageTemplateManager.save(messageTemplate);
		return result;
	}

	@RequestMapping(value = "findMessageTemplateDetail")
	@ResponseBody
	public AsyncResponse findMessageTemplateDetail(String fldId) {
		AsyncResponse response = new AsyncResponse();
		List<MessageTemplate> list = new ArrayList<MessageTemplate>();
		MessageTemplate messageTemplate = messageTemplateManager.find(fldId);
		list.add(messageTemplate);
		response.addData(list);
		response.setIsError(false);
		return response;
	}
}