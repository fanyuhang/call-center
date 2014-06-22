package com.common.security.context;

import com.common.security.entity.Dept;
import com.common.security.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户在session中数据
 * User: Allen
 * Date: 9/20/12
 */
public class UserContext implements Serializable {

    private User user;

    private Dept dept;

    private Date lastLoginTime;

    public UserContext() {
        lastLoginTime = new Date();
    }

    public UserContext(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
