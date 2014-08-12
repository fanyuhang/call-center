package com.redcard.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	// public MessageTemplate find(String fldId) {
	// return messageTemplateDao.findOne(fldId);
	// }
	//
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
	//
	// public Page<MessageTemplate> queryMessageTemplates(GridPageRequest page,
	// String where) {
	// return (Page<MessageTemplate>) super.findAll(where, page);
	// }

}