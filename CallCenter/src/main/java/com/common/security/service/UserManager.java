package com.common.security.service;

import com.common.Constant;
import com.common.core.util.GenericPageHQLQuery;
import com.common.core.util.SystemEnum;
import com.common.security.dao.*;
import com.common.security.entity.*;
import com.common.security.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Transactional(readOnly = true)
public class UserManager extends GenericPageHQLQuery<User> {

    private static Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDeptDao userDeptDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CertActiveDao certActiveDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserDeptDao(UserDeptDao userDeptDao) {
        this.userDeptDao = userDeptDao;
    }

    public void setCertActiveDao(CertActiveDao certActiveDao) {
        this.certActiveDao = certActiveDao;
    }

    public List<User> findAllUser() {
        return (List<User>) userDao.findAll();
    }

    public Page<User> findAllUser(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    public User findUser(Integer id) {
        return userDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user, String deptCode, String roleIds) {
        if (user.getId() != null) {
            userDeptDao.deleteUserDeptByUser(user.getLoginName());
            userRoleDao.deleteUserRoleByUser(user.getId());
        }

        userDao.save(user);
        if (StringUtils.isNotEmpty(deptCode)) {
            UserDept userDept = new UserDept();
            userDept.setLoginName(user.getLoginName());
            userDept.setDeptCode(deptCode);
            userDept.setSystem(SystemEnum.SYSTEM_SELF.toString());
            userDeptDao.save(userDept);
        }
        if (StringUtils.isNotBlank(roleIds)) {
            String[] roleArray = StringUtils.split(roleIds, ",");
            for (String roleId : roleArray) {
                if (StringUtils.isNumeric(roleId)) {
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(Integer.parseInt(roleId));
                    userRole.setUserId(user.getId());
                    userRoleDao.save(userRole);
                }
            }
        }
    }

    @Transactional(readOnly = false)
    public void deleteUser(Integer id) {
        User user = userDao.findOne(id);
        favoriteDao.deleteByUserId(id);
        userDeptDao.deleteUserDeptByUser(user.getLoginName());
        userRoleDao.deleteUserRoleByUser(id);
        userDao.delete(id);
    }

    public User findUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    public UserDept findUserDeptByUser(Integer userId) {
        User user = userDao.findOne(userId);
        List<UserDept> result = userDeptDao.findUserDeptByUser(user.getLoginName());
        return result.size() > 0 ? result.get(0) : null;
    }

    public List<UserRole> findUserRoleByUser(Integer userId) {
        return userRoleDao.findUserRoleByUser(userId);
    }

    public List<Role> findRoleByUser(Integer userId) {
        return roleDao.findRoleByUser(userId);
    }

    public List<String> findRoleNameByUser(Integer userId) {
        return roleDao.findRoleNameByUser(userId);
    }

    @Transactional(readOnly = false)
    public void updatePassword(String password, Integer userId) {
        userDao.updatePassword(password, userId);
    }

    @Transactional(readOnly = false)
    public void updateLoginStatus(Integer loginStatus, Integer userId) {
        userDao.updateLoginStatus(loginStatus, userId);
    }

    @Transactional(readOnly = false)
    public void updateUserStatus(Integer userStatus, Integer userId) {
        userDao.updateUserStatus(userStatus, userId);
    }

    @Transactional(readOnly = false)
    public void unlockUser(Integer userId) {
        User user = userDao.findOne(userId);
        if (user == null)
            return;
        user.setUserStatus(Constant.USER_STATUS_NORMAL);
        user.setLoginErrCount(0);
    }

    @Transactional(readOnly = false)
    public void updateLoginStatusAndTime(Integer loginStatus, Date lastLoginTime, Integer userId) {
        //also update login error count
        userDao.updateLoginStatusAndTime(loginStatus, lastLoginTime, userId);
    }

    public boolean existUserCert(String certCN, Integer userId) {
        User existUser = userDao.findByCertCN(certCN);
        if (existUser != null && !existUser.getId().equals(userId)) {
            return true;
        }
        return false;
    }

    public boolean existCert(String certCN) {
        CertActive certActive = certActiveDao.findOne(certCN);
        if (certActive != null)
            return true;
        return false;
    }

    @Transactional(readOnly = false)
    public void activeCert(Integer userId, CertActive certActive) {
        User user = userDao.findOne(userId);
        if (StringUtils.isNotBlank(user.getCertCN())) {
            //如果用户usbkey已激活，删除原有激活记录
            certActiveDao.delete(user.getCertCN());
        }
        certActiveDao.save(certActive);
        userDao.updateUserCN(certActive.getCertCN(), userId);
    }

    @Transactional(readOnly = false)
    public void activeCert(CertActive certActive) {
        certActiveDao.save(certActive);
    }

    public List<CertActive> findAvailableCert() {
        return certActiveDao.findAvailableCert();
    }

    @Transactional(readOnly = false)
    public void updateLoginErrorCount(Integer loginErrorCount) {
        userDao.updateLoginErrorCount(loginErrorCount);
    }

    @Transactional(readOnly = false)
    public void logoutAllUser() {
        userDao.resetAllLoginStatus(Constant.LOGIN_STATUS_LOGOUT);
    }

    @Transactional(readOnly = false)
    public void logoutCurrentUser() {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId != null) {
            userDao.resetLoginStatus(userId, Constant.LOGIN_STATUS_LOGOUT);
        }
    }

    public List<User> findByUserName(String userName) {
        return userDao.findByUserName("%" + userName + "%");
    }

    public boolean existUser(String loginName) {
        return userDao.findByLoginName(loginName) != null;
    }
}
