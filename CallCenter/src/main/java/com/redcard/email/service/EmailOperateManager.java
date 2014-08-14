package com.redcard.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.google.gson.Gson;
import com.redcard.email.dao.EmailOperateDao;
import com.redcard.email.entity.EmailOperate;

@Component
public class EmailOperateManager extends GenericPageHQLQuery<EmailOperate> {

	private static Logger logger = LoggerFactory.getLogger(EmailOperateManager.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailOperateDao emailOperateDao;

	@Transactional(readOnly = false)
	public void save(EmailOperate emailOperate) {
		emailOperateDao.save(emailOperate);
	}

	public EmailOperate find(String fldId) {
		return emailOperateDao.findOne(fldId);
	}

	public Page<EmailOperate> queryEmailOperates(GridPageRequest page, String where) {
		return (Page<EmailOperate>) super.findAll(where, page);
	}

	public void sendEmails(String subject, String sendUserEmail, String[] receiveUserEmail, String emailContent) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setSubject(subject);
		smm.setFrom(sendUserEmail);
		smm.setTo(receiveUserEmail);
		smm.setText(emailContent);
		logger.info("simpleMailMessage is:" + new Gson().toJson(smm));
		mailSender.send(smm);
	}

}
