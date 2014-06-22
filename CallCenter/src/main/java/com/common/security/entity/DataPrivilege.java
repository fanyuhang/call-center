package com.common.security.entity;

import com.common.core.annotation.Comment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Allen
 * Date: 9/13/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tbldataprivilege")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Comment(value = "数据权限")
public class DataPrivilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flddataprivilegeid")
    private Integer id;

    @Column(name = "flddataprivilegemaster")
    private String master;

    @Column(name = "flddataprivilegerule")
    private String privilegeRule;

    public DataPrivilege() {
    }

    public DataPrivilege(String master, String privilegeRule) {
        this.master = master;
        this.privilegeRule = privilegeRule;
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

    public String getPrivilegeRule() {
        return privilegeRule;
    }

    public void setPrivilegeRule(String privilegeRule) {
        this.privilegeRule = privilegeRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPrivilege that = (DataPrivilege) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (master != null ? !master.equals(that.master) : that.master != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (master != null ? master.hashCode() : 0);
        return result;
    }
}
