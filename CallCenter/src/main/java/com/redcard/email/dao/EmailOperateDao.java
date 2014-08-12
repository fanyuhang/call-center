package com.redcard.email.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.message.entity.MessageOperate;

public interface EmailOperateDao extends PagingAndSortingRepository<MessageOperate, String> {

}
