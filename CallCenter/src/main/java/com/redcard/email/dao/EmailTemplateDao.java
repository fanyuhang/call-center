package com.redcard.email.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.email.entity.EmailTemplate;

public interface EmailTemplateDao extends PagingAndSortingRepository<EmailTemplate, String> {

	@Query("select count(e) from EmailTemplate e where e.fldName = ?1")
	public Long countByTemplateName(String fldName);

	@Query("select m from MessageTemplate m where m.fldStatus = 0")
	public List<EmailTemplate> findAll();
}
