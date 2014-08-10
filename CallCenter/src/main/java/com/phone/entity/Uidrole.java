package com.phone.entity;

import javax.persistence.*;

/**
 * Created by thinkpad on 14-8-2.
 */
@Entity
public class Uidrole {
    private Integer id;
    private String uid;
    private Integer roleid;

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
    @Column(name = "roleid")
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Uidrole uidrole = (Uidrole) o;

        if (id != uidrole.id) return false;
        if (roleid != null ? !roleid.equals(uidrole.roleid) : uidrole.roleid != null) return false;
        if (uid != null ? !uid.equals(uidrole.uid) : uidrole.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        return result;
    }
}
