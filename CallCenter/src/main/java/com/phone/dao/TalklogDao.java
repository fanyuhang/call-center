package com.phone.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.phone.entity.Talklog;

public interface TalklogDao extends PagingAndSortingRepository<Talklog, Long> {

    @Query("select t from Talklog t where t.callid = ?1")
    public Talklog findByCallId(Long callId);

}
