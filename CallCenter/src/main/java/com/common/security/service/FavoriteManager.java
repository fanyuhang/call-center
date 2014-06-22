package com.common.security.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.dao.FavoriteDao;
import com.common.security.dao.PrivilegeDao;
import com.common.security.dao.UserRoleDao;
import com.common.security.entity.Favorite;
import com.common.security.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class FavoriteManager extends GenericPageHQLQuery<Favorite> {

    private static Logger logger = LoggerFactory.getLogger(FavoriteManager.class);

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Transactional(readOnly = false)
    public void deleteByUserId(Integer userId) {
        favoriteDao.deleteByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void deleteByNodeCode(String nodeCode) {
        favoriteDao.deleteByNodeCode(nodeCode);
    }

    @Transactional(readOnly = false)
    public void save(Favorite favorite){
        favoriteDao.save(favorite);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id){
        favoriteDao.delete(id);
    }

    public List<Favorite> findByUserId(Integer userId) {
        return favoriteDao.findByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void updateByUserId(Integer userId) {
        List<Favorite> list = favoriteDao.findByUserId(userId);
        for (Favorite f : list) {
            if (privilegeDao.findPrivilegeByUserIdAndCode(userId, f.getNodeCode()).isEmpty()) {
                favoriteDao.deleteByUserIdAndCode(userId, f.getNodeCode());
            }
        }
    }

    @Transactional(readOnly = false)
    public void updateByRoleId(Integer roleId){
         List<UserRole> list = userRoleDao.findUserRoleByRole(roleId);
         for(UserRole ur : list){
             updateByUserId(ur.getUserId());
         }
    }
}
