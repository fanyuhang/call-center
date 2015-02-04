package com.phone.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by thinkpad on 14-8-2.
 */
@Entity
public class Operator implements Serializable {

    private static final long serialVersionUID = -4524376173769866478L;
    private Integer id;
    private String uid;
    private String pwd;
    private String name;
    private String dialpwd;
    private Integer rightlevel;
    private Integer dialgrp;
    private Integer confgrp;
    private int pausedir;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dialpwd")
    public String getDialpwd() {
        return dialpwd;
    }

    public void setDialpwd(String dialpwd) {
        this.dialpwd = dialpwd;
    }

    @Basic
    @Column(name = "rightlevel")
    public Integer getRightlevel() {
        return rightlevel;
    }

    public void setRightlevel(Integer rightlevel) {
        this.rightlevel = rightlevel;
    }

    @Basic
    @Column(name = "dialgrp")
    public Integer getDialgrp() {
        return dialgrp;
    }

    public void setDialgrp(Integer dialgrp) {
        this.dialgrp = dialgrp;
    }

    @Basic
    @Column(name = "confgrp")
    public Integer getConfgrp() {
        return confgrp;
    }

    public void setConfgrp(Integer confgrp) {
        this.confgrp = confgrp;
    }

    @Basic
    @Column(name = "pausedir")
    public int getPausedir() {
        return pausedir;
    }

    public void setPausedir(int pausedir) {
        this.pausedir = pausedir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator operator = (Operator) o;

        if (id != operator.id) return false;
        if (pausedir != operator.pausedir) return false;
        if (confgrp != null ? !confgrp.equals(operator.confgrp) : operator.confgrp != null) return false;
        if (dialgrp != null ? !dialgrp.equals(operator.dialgrp) : operator.dialgrp != null) return false;
        if (dialpwd != null ? !dialpwd.equals(operator.dialpwd) : operator.dialpwd != null) return false;
        if (name != null ? !name.equals(operator.name) : operator.name != null) return false;
        if (pwd != null ? !pwd.equals(operator.pwd) : operator.pwd != null) return false;
        if (rightlevel != null ? !rightlevel.equals(operator.rightlevel) : operator.rightlevel != null) return false;
        if (uid != null ? !uid.equals(operator.uid) : operator.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dialpwd != null ? dialpwd.hashCode() : 0);
        result = 31 * result + (rightlevel != null ? rightlevel.hashCode() : 0);
        result = 31 * result + (dialgrp != null ? dialgrp.hashCode() : 0);
        result = 31 * result + (confgrp != null ? confgrp.hashCode() : 0);
        result = 31 * result + pausedir;
        return result;
    }
}
