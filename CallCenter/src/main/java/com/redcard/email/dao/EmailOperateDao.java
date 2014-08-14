package com.redcard.email.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.email.entity.EmailOperate;

public interface EmailOperateDao extends PagingAndSortingRepository<EmailOperate, String> {

}
