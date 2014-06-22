package com.common.security.dao;

import com.common.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * User: Allen
 * Date: 9/2/12
 */
public interface UserDao extends PagingAndSortingRepository<User, Integer> {

    public User findByLoginName(String loginName);

    @Query("select u from User u where u.userName like ?1 ")
    public List<User> findByUserName(String UserName);

    @Modifying
    @Query("update User u set u.password=?1,u.modifyPwdCount=u.modifyPwdCount+1 where u.id=?2")
    public void updatePassword(String password, Integer userId);

    @Modifying
    @Query("update User u set u.loginStatus=?1 where u.id=?2")
    public void updateLoginStatus(Integer loginStatus, Integer userId);

    @Modifying
    @Query("update User u set u.userStatus=?1 where u.id=?2")
    public void updateUserStatus(Integer userStatus, Integer userId);

    @Modifying
    @Query("update User u set u.loginStatus=?1,u.lastLoginTime=?2,u.loginErrCount=0 where u.id=?3")
    public void updateLoginStatusAndTime(Integer loginStatus, Date lastLoginTime, Integer userId);

    @Modifying
    @Query("update User u set u.certCN=?1 where u.id=?2")
    public void updateUserCN(String cn, Integer userId);

    public User findByCertCN(String cn);

    @Modifying
    @Query("update User u set u.loginErrCount=?1")
    public void updateLoginErrorCount(Integer loginErrorCount);

    @Modifying
    @Query("update User u set u.loginStatus=?1")
    public void resetAllLoginStatus(Integer loginStatus);

    @Modifying
    @Query("update User u set u.loginStatus=?2 where u.id=?1")
    public void resetLoginStatus(Integer userId, Integer loginStatus);

    @Query("select u from User u where u.certCN in (?1)")
    public List<User> findByCertCN(List<String> certCNs);

}
