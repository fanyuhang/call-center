package com.redcard.system.service;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.system.dao.SysParameterDao;
import com.redcard.system.entity.SysParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: Allen
 * Date: 1/19/13
 */
@Component
public class SysParameterManager extends GenericPageHQLQuery<SysParameter> {

    private static Logger logger = LoggerFactory.getLogger(SysParameterManager.class);

    @Autowired
    private SysParameterDao sysParameterDao;

    public void setSysParameterDao(SysParameterDao sysParameterDao) {
        this.sysParameterDao = sysParameterDao;
    }

    public List<SysParameter> findAllParameter() {
        return (List<SysParameter>) this.sysParameterDao.findAll();
    }

    public void save(SysParameter sysParameter) {
        sysParameterDao.save(sysParameter);
    }

    public SysParameter findParameter(Integer id) {
        return sysParameterDao.findOne(id);
    }

    public List<SysParameter> findCachedParameter() {
        return this.sysParameterDao.findCachedParameter();
    }
}
