package com.common.security.service;

import com.common.Constant;
import com.common.security.dao.PrivilegeDao;
import com.common.security.entity.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Allen
 * Date: 9/12/12
 */
@Component
@Transactional(readOnly = true)
public class PrivilegeManager {

    private static Logger logger = LoggerFactory.getLogger(PrivilegeManager.class);

    @Autowired
    private PrivilegeDao privilegeDao;

    public void setPrivilegeDao(PrivilegeDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

    @Transactional(readOnly = false)
    public void savePrivilege(Privilege privilege) {
        privilegeDao.save(privilege);
    }

    /**
     * 查找角色对应的权限
     *
     * @param roleId 角色ID
     * @return 角色权限集合
     */
    public List<Privilege> findRolePrivilege(Integer roleId) {
        return privilegeDao.findMasterPrivilege(Constant.PRIVILEGE_MASTER_ROLE, roleId);
    }

    /**
     * 查找用户对应的权限，不包括用户从角色处继承来的权限
     *
     * @param userId 用户ID
     * @return 用户权限集合
     */
    public List<Privilege> findUserPrivilege(Integer userId) {
        return privilegeDao.findMasterPrivilege(Constant.PRIVILEGE_MASTER_USER, userId);
    }

    /**
     * 查找用户对应的权限，结果为用户自身权限和从角色处继承权限的合集
     *
     * @param userId 用户ID
     * @return 用户权限集合, key 为accessValue
     */
    public Map<String, Privilege> findUserPermission(Integer userId) {
        List<Privilege> userRolePrivileges = privilegeDao.findUserRolePrivilege(userId);
        List<Privilege> userPrivileges = privilegeDao.findMasterPrivilege(Constant.PRIVILEGE_MASTER_USER, userId);

        Map<String, Privilege> map = new HashMap<String, Privilege>();
        //角色权限没有禁止权限，只有分配权限
        for (Privilege userRolePrivilege : userRolePrivileges) {
            map.put(userRolePrivilege.getAccessValue(), userRolePrivilege);
        }
        for (Privilege userPrivilege : userPrivileges) {
            if (Constant.PRIVILEGE_OPERATION_PERMIT == userPrivilege.getOperation()) {
                map.put(userPrivilege.getAccessValue(), userPrivilege);
            } else {
                if (map.containsKey(userPrivilege.getAccessValue())) {
                    map.remove(userPrivilege.getAccessValue());
                }
            }
        }
        return map;
    }

    /**
     * 查找具体用户是否具有具体的权限，如果没有权限，list为空
     *
     * @param userId 用户ID
     * @param code   功能code
     * @return 具体的权限
     */
    public List<Privilege> findUserPrivilege(Integer userId, String code) {
        return privilegeDao.findPrivilegeByUserIdAndCode(userId, code);
    }

    /**
     * 保存角色权限
     *
     * @param roleId      角色ID
     * @param permissions 权限集合
     */
    @Transactional(readOnly = false)
    public void saveRolePrivilege(Integer roleId, List<Map> permissions) {
        Object code, permit;
        //先删除角色对应的所有权限，在解析权限数据，保存角色对应的权限
        privilegeDao.deleteMasterPrivilege(Constant.PRIVILEGE_MASTER_ROLE, roleId);
        for (Map permission : permissions) {
            code = permission.get("code");
            permit = permission.get("permit");
            if (code == null || permit == null) {
                continue;
            }
            if ("false".equals(permit.toString())) {
                continue;
            }
            Privilege privilege = new Privilege();
            privilege.setMaster(Constant.PRIVILEGE_MASTER_ROLE);
            privilege.setMasterValue(roleId);
            privilege.setAccessValue(code.toString());
            privilege.setOperation(Constant.PRIVILEGE_OPERATION_PERMIT);
            privilegeDao.save(privilege);
        }
    }

    /**
     * 保存用户权限
     *
     * @param userId      用户ID
     * @param permissions 权限集合
     */
    @Transactional(readOnly = false)
    public void saveUserPrivilege(Integer userId, List<Map> permissions) {
        Object code, permit, forbid;
        boolean isPermit, isForbid;
        //先删除用户对应的所有权限，在解析权限数据，保存用户对应的权限
        privilegeDao.deleteMasterPrivilege(Constant.PRIVILEGE_MASTER_USER, userId);
        for (Map permission : permissions) {
            code = permission.get("code");
            permit = permission.get("permit");
            forbid = permission.get("forbid");
            if (code == null) {
                continue;
            }
            //isPermit和isForbid默认为false
            //如果既选择了允许，又选择了禁止，那么只保存允许
            isPermit = (permit != null && "true".equals(permit.toString())) ? true : false;
            isForbid = (forbid != null && "true".equals(forbid.toString())) ? true : false;
            if (isPermit || isForbid) {
                Privilege privilege = new Privilege();
                privilege.setMaster(Constant.PRIVILEGE_MASTER_USER);
                privilege.setMasterValue(userId);
                privilege.setAccessValue(code.toString());
                //允许设置为1，不允许设置为0
                privilege.setOperation(isPermit ? Constant.PRIVILEGE_OPERATION_PERMIT : Constant.PRIVILEGE_OPERATION_FORBID);
                privilegeDao.save(privilege);
            }
        }

    }

}
