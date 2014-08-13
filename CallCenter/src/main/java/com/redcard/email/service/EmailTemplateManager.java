package com.redcard.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.redcard.email.dao.EmailTemplateDao;
import com.redcard.email.entity.EmailTemplate;

@Component
public class EmailTemplateManager extends GenericPageHQLQuery<EmailTemplate> {

	// private static Logger logger =
	// LoggerFactory.getLogger(MessageTemplateManager.class);

	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Transactional(readOnly = false)
	public void save(EmailTemplate emailTemplate) {
		emailTemplateDao.save(emailTemplate);
	}

	public EmailTemplate find(String fldId) {
		return emailTemplateDao.findOne(fldId);
	}

	// public List<MessageTemplate> findAll() {
	// return messageTemplateDao.findAll();
	// }
	//
	public boolean isExistEmailTemplate(EmailTemplate emailTemplate) {
		Long emailTemplateCount = emailTemplateDao.countByTemplateName(emailTemplate.getFldName());
		if (emailTemplateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Page<EmailTemplate> queryEmailTemplates(GridPageRequest page, String where) {
		return (Page<EmailTemplate>) super.findAll(where, page);
	}

}