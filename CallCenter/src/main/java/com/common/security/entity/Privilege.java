package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 9/12/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tblprivilege")
@Comment(value = "功能权限")
public class Privilege implements Auditable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldprivilegeid")
    private Integer id;

    @Column(name = "fldprivilegemaster")
    private String master;

    @Column(name = "fldprivilegemastervalue")
    private Integer masterValue;

    @Column(name = "fldprivilegeaccessvalue")
    private String accessValue;

    @Column(name = "fldprivilegeoperation")
    private Integer operation;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    public Privilege() {
    }

    public Privilege(Integer id, String master, Integer masterValue, String accessValue, Integer operation) {
        this.id = id;
        this.master = master;
        this.masterValue = masterValue;
        this.accessValue = accessValue;
        this.operation = operation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Integer getMasterValue() {
        return masterValue;
    }

    public void setMasterValue(Integer masterValue) {
        this.masterValue = masterValue;
    }

    public String getAccessValue() {
        return accessValue;
    }

    public void setAccessValue(String accessValue) {
        this.accessValue = accessValue;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilege privilege = (Privilege) o;

        if (masterValue != privilege.masterValue) return false;
        if (accessValue != null ? !accessValue.equals(privilege.accessValue) : privilege.accessValue != null)
            return false;
        if (id != null ? !id.equals(privilege.id) : privilege.id != null) return false;
        if (master != null ? !master.equals(privilege.master) : privilege.master != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (master != null ? master.hashCode() : 0);
        result = 31 * result + masterValue;
        result = 31 * result + (accessValue != null ? accessValue.hashCode() : 0);
        return result;
    }
}
