package com.redcard.message.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.message.entity.MessageTemplate;

import java.util.List;

public interface MessageTemplateDao extends PagingAndSortingRepository<MessageTemplate, String> {

    @Query("select count(mt) from MessageTemplate mt where mt.fldName = ?1")
    public Long countByTemplateName(String fldName);

    @Query("select m from MessageTemplate m where m.fldStatus = 0")
    public List<MessageTemplate> findAll();
}
