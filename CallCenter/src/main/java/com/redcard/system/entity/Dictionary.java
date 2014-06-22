package com.redcard.system.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 10/28/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tbldictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Comment(value = "数据字典")
public class Dictionary implements Auditable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldtype")
    private Integer type;

    @Column(name = "fldname")
    private String name;

    @Column(name = "fldvalue")
    private String value;

    @Column(name = "fldstatus")
    private Integer status;

    @Column(name = "fldcomment")
    private String comment;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    public Dictionary() {
    }

    public Dictionary(Integer type, String name, String value, Integer status) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

        Dictionary that = (Dictionary) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
