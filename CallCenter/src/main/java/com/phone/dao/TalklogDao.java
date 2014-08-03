package com.phone.dao;

import com.phone.entity.Calllog;
import com.phone.entity.Talklog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TalklogDao extends PagingAndSortingRepository<Talklog, Integer> {

    @Query("select t from Talklog t where t.callid = ?1")
    public Talklog findByCallId(Integer callId);

}
