package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息
 * User: Allen
 * Date: 9/2/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tblrole")
@Comment(value = "角色")
public class Role implements Auditable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldrolename")
    private String roleName;

    @Column(name = "fldposition")
    private Integer position;

    @Column(name = "flddesc")
    private String description;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    public Role() {
    }

    public Role(Integer id, String roleName, Integer position, String description, Integer operateId, Date operateDate) {
        this.id = id;
        this.roleName = roleName;
        this.position = position;
        this.description = description;
        this.operateId = operateId;
        this.operateDate = operateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        if (roleName != null ? !roleName.equals(role.roleName) : role.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }
}
