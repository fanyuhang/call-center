package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 部门信息
 * User: Allen
 * Date: 9/2/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tbldept")
@Comment(value = "部门")
public class Dept implements Auditable, Serializable {

    @Id
    @Column(name = "flddeptcode")
    private String deptCode;

    @Column(name = "flddeptname")
    private String deptName;

    @Column(name = "fldposition")
    private Integer position;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    @Column(name = "fldparent")
    private String parent;

    @Column(name = "fldgeneratedate")
    private Date generateDate;

    @Column(name = "fldsystem")
    private String system;

    @Transient
    List<Dept> children = new ArrayList<Dept>();

    public Dept() {
    }

    public Dept(String deptCode, String deptName, Integer position, Integer operateId, Date operateDate) {
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.position = position;
        this.operateId = operateId;
        this.operateDate = operateDate;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public String getParent() {
        return parent;
    }

    public void setChildren(Collection<Dept> children) {
        this.children.addAll(children);
    }

    public Collection<Dept> getChildren() {
        return children;
    }

    public void addChild(Dept entity) {
        children.add(entity);
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dept dept = (Dept) o;

        if (!deptCode.equals(dept.deptCode)) return false;
        if (deptName != null ? !deptName.equals(dept.deptName) : dept.deptName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptCode.hashCode();
        result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
        return result;
    }

	@Override
	public String toString() {
		return "Dept [deptCode=" + deptCode + ", deptName=" + deptName + "]";
	}
    
}
