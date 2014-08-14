package com.redcard.email.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.filter.FilterGroup;
import com.common.core.filter.FilterRule;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.JsonHelper;
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

	@RequestMapping(value = "conditionalList")
	@ResponseBody
	public DataResponse<EmailTemplate> conditionalList(GridPageRequest pageRequest, String where) {
		pageRequest.setSort("fldOperateDate", "desc");
		if (StringUtils.isEmpty(where)) {
			where = "{\"op\":\"and\",\"rules\":[{\"op\":\"equal\",\"field\":\"fldStatus\",\"value\":\"0\",\"type\":\"int\"}]}";
		} else {
			FilterGroup filterGroup = JsonHelper.deserialize(where, FilterGroup.class);
			List<FilterRule> filterRules = filterGroup.getRules();
			if (!CollectionUtils.isEmpty(filterRules)) {
				FilterRule statusFilterRule = new FilterRule();
				statusFilterRule.setField("fldStatus");
				statusFilterRule.setOp("equal");
				statusFilterRule.setType("int");
				statusFilterRule.setValue("0");
				filterRules.add(statusFilterRule);
			}
			where = JsonHelper.serialize(filterGroup);
		}
		return (new DataResponse<EmailTemplate>(emailTemplateManager.queryEmailTemplates(pageRequest, where)));
	}

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
}