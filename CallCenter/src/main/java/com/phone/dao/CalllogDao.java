package com.phone.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.phone.entity.Calllog;

import java.util.Date;
import java.util.List;

public interface CalllogDao extends PagingAndSortingRepository<Calllog, Long> {

    @Query("select c from Calllog c where c.callerid = ?1 and c.agentId=?2 and c.inboundCallTime>?3 order by c.inboundCallTime desc")
    public List<Calllog> findByPhoneAndCaller(String phone, String caller,Date date);
}
