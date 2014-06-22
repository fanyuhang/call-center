package com.common.security.service;

import com.common.security.dao.DataPrivilegeDao;
import com.common.security.entity.DataPrivilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Allen
 * Date: 9/13/12
 */
@Component
@Transactional(readOnly = true)
public class DataPrivilegeManager {

    private static Logger logger = LoggerFactory.getLogger(DataPrivilegeManager.class);

    @Autowired
    private DataPrivilegeDao dataPrivilegeDao;

    public void setDataPrivilegeDao(DataPrivilegeDao dataPrivilegeDao) {
        this.dataPrivilegeDao = dataPrivilegeDao;
    }

    @Cacheable(value = "DICT_CACHE", key = "getSystemDictByDictId")
    public List<DataPrivilege> findAllDataPrivilege() {
        return (List<DataPrivilege>) dataPrivilegeDao.findAll();
    }

    public DataPrivilege findDataPrivilege(Integer id) {
        return dataPrivilegeDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void saveDataPrivilege(DataPrivilege dataPrivilege) {
        dataPrivilegeDao.save(dataPrivilege);
    }

    @Transactional(readOnly = false)
    public void deleteDataPrivilege(Integer id) {
        dataPrivilegeDao.delete(id);
    }

    /**
     * 判断对应的master是否已设置了数据权限
     *
     * @param master 数据权限主题
     * @return true 表示对应的master已经设置了数据权限
     */
    public boolean isMasterExist(String master) {
        return dataPrivilegeDao.findByMaster(master).size() > 0;
    }

    public List<DataPrivilege> findCachedDataPrivilege() {
        return dataPrivilegeDao.findCachedDataPrivilege();
    }
}
