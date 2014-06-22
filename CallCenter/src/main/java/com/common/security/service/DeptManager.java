package com.common.security.service;

import com.common.core.exception.ServiceException;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.dao.DeptDao;
import com.common.security.dao.UserDeptDao;
import com.common.security.entity.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class DeptManager extends GenericPageHQLQuery<Dept> {

    private static Logger logger = LoggerFactory.getLogger(DeptManager.class);

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private UserDeptDao userDeptDao;

    public void setDeptDao(DeptDao deptDao) {
        this.deptDao = deptDao;
    }

    public void setUserDeptDao(UserDeptDao userDeptDao) {
        this.userDeptDao = userDeptDao;
    }

    public List<Dept> findAllDept() {
        return (List<Dept>) deptDao.findAllDept();
    }

    public Page<Dept> findAllDept(Pageable pageable) {
        return deptDao.findAll(pageable);
    }

    public Dept findDept(String code) {
        return deptDao.findOne(code);
    }

    @Transactional(readOnly = false)
    public void saveDept(Dept dept) {
        deptDao.save(dept);
    }

    @Transactional(readOnly = false)
    public void deleteDept(String code) {
        List<Dept> children = deptDao.findChildren(code);
        if (children != null && children.size() > 0) {
            throw new ServiceException("该部门存在下级部门");
        }
        deptDao.delete(code);
    }

    public String findMaxChildCode(String parent) {
        return deptDao.findMaxChildCode(parent);
    }

    public Dept findDeptByUser(String loginName) {
        List<Dept> result = deptDao.findDeptByUser(loginName);
        return result.size() > 0 ? result.get(0) : null;
    }
}
