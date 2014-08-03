package com.redcard.message.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.redcard.message.entity.MessageOperate;

import java.util.List;

public interface MessageOperateDao extends PagingAndSortingRepository<MessageOperate, String> {

}
