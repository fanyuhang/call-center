package com.phone.entity;

import javax.persistence.*;

/**
 * Created by thinkpad on 14-8-2.
 */
@Entity
public class Userlist {
    private int id;
    private int ch;
    private String usercode;
    private int userpos;
    private int hotline;
    private int bindtrk;
    private boolean recflag;
    private String memo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ch")
    public int getCh() {
        return ch;
    }

    public void setCh(int ch) {
        this.ch = ch;
    }

    @Basic
    @Column(name = "usercode")
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Basic
    @Column(name = "userpos")
    public int getUserpos() {
        return userpos;
    }

    public void setUserpos(int userpos) {
        this.userpos = userpos;
    }

    @Basic
    @Column(name = "hotline")
    public int getHotline() {
        return hotline;
    }

    public void setHotline(int hotline) {
        this.hotline = hotline;
    }

    @Basic
    @Column(name = "bindtrk")
    public int getBindtrk() {
        return bindtrk;
    }

    public void setBindtrk(int bindtrk) {
        this.bindtrk = bindtrk;
    }

    @Basic
    @Column(name = "recflag")
    public boolean isRecflag() {
        return recflag;
    }

    public void setRecflag(boolean recflag) {
        this.recflag = recflag;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userlist userlist = (Userlist) o;

        if (bindtrk != userlist.bindtrk) return false;
        if (ch != userlist.ch) return false;
        if (hotline != userlist.hotline) return false;
        if (id != userlist.id) return false;
        if (recflag != userlist.recflag) return false;
        if (userpos != userlist.userpos) return false;
        if (memo != null ? !memo.equals(userlist.memo) : userlist.memo != null) return false;
        if (usercode != null ? !usercode.equals(userlist.usercode) : userlist.usercode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ch;
        result = 31 * result + (usercode != null ? usercode.hashCode() : 0);
        result = 31 * result + userpos;
        result = 31 * result + hotline;
        result = 31 * result + bindtrk;
        result = 31 * result + (recflag ? 1 : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        return result;
    }
}
