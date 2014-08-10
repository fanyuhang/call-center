package com.phone.dao.mybatis;

import com.phone.entity.ExtMonitor;
import com.phone.entity.TrunkMonitor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneMapper {
    public List<ExtMonitor> queryExtMonitor();
}
