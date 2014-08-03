package com.phone.entity;

import javax.persistence.*;

/**
 * Created by thinkpad on 14-8-2.
 */
@Entity
public class Roleright {
    private int id;
    private Integer roleid;
    private Integer rightid;

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
    @Column(name = "roleid")
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Basic
    @Column(name = "rightid")
    public Integer getRightid() {
        return rightid;
    }

    public void setRightid(Integer rightid) {
        this.rightid = rightid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Roleright roleright = (Roleright) o;

        if (id != roleright.id) return false;
        if (rightid != null ? !rightid.equals(roleright.rightid) : roleright.rightid != null) return false;
        if (roleid != null ? !roleid.equals(roleright.roleid) : roleright.roleid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleid != null ? roleid.hashCode() : 0);
        result = 31 * result + (rightid != null ? rightid.hashCode() : 0);
        return result;
    }
}
