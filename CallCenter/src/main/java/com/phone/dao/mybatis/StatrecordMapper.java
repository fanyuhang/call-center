package com.phone.dao.mybatis;

import com.phone.entity.ExtMonitor;
import com.phone.entity.Statrecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatrecordMapper {
    public List<Statrecord> operatorStat();
}
