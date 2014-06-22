package com.common.security.entity;

import com.common.core.annotation.Comment;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 9/2/12
 */
@Entity
@Table(name = "tbluserdeptlink")
@Comment(value = "用户部门信息")
public class UserDept implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldloginname")
    private String loginName;

    @Column(name = "flddeptcode")
    private String deptCode;

    @Column(name = "fldgeneratedate")
    private Date generateDate;

    @Column(name = "fldsystem")
    private String system;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flddeptcode", insertable = false, updatable = false)
    private Dept dept;

    public UserDept() {
    }

    public UserDept(String loginName, String deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
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

        UserDept userDept = (UserDept) o;

        if (deptCode != null ? !deptCode.equals(userDept.deptCode) : userDept.deptCode != null) return false;
        if (id != null ? !id.equals(userDept.id) : userDept.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        return result;
    }
}
