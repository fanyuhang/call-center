package com.redcard.message.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.message.entity.MessageOperate;

public interface MessageOperateDao extends PagingAndSortingRepository<MessageOperate, String> {

}
