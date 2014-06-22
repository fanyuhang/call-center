package com.common.security.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.dao.RoleDao;
import com.common.security.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class RoleManager extends GenericPageHQLQuery<Role>{

    private static Logger logger = LoggerFactory.getLogger(RoleManager.class);

    @Autowired
    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> findAll() {
        return (List<Role>) roleDao.findAll();
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleDao.findAll(pageable);
    }

    public Role find(Integer id) {
        return roleDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Role entity) {
        roleDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        roleDao.delete(id);
    }

    /**
     * 校验入参role是否已在数据库中有相同roleName记录
     *
     * @param role 待校验role
     * @return true 表示有相同roleName记录
     */
    public boolean isExistRole(Role role) {
        List<Role> list = roleDao.findRoleByRoleName(role.getRoleName());
        if (list.size() > 0) {
            Role dbRole = list.get(0);
            if (dbRole.getRoleName().equals(role.getRoleName()) && !dbRole.getId().equals(role.getId())) {
                return true;
            }
        }
        return false;
    }
}