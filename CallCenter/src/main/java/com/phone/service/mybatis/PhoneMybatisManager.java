package com.phone.service.mybatis;

import com.phone.dao.mybatis.PhoneMapper;
import com.phone.entity.ExtMonitor;
import com.phone.entity.TrunkMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneMybatisManager {

    @Autowired
    private PhoneMapper phoneMapper;

    public List<ExtMonitor> queryExtMonitor(){
        return phoneMapper.queryExtMonitor();
    }

}
