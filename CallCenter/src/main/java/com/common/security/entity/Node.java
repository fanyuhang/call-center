package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能节点信息
 * User: Allen
 * Date: 9/2/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tblnode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Comment(value = "模块")
public class Node implements Auditable, Serializable {

    @Id
    @Column(name = "fldcode")
    private String code;

    @Column(name = "fldname")
    private String name;

    @Column(name = "flddesc")
    private String desc;

    @Column(name = "fldtarget")
    private String target;

    @Column(name = "fldicon")
    private String icon;

    @Column(name = "fldlink")
    private String link;

    @Column(name = "fldtype")
    private Integer type;

    @Column(name = "fldposition")
    private Integer position;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    @Column(name = "fldparent")
    private String parent;

    @Transient
    List<Node> children = new ArrayList<Node>();

    public Node() {
    }

    public Node(String code, String name, String desc, String target, String icon, String link, Integer type, Integer position, Integer operateId, Date operateDate) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.target = target;
        this.icon = icon;
        this.link = link;
        this.type = type;
        this.position = position;
        this.operateId = operateId;
        this.operateDate = operateDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node entity) {
        children.add(entity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!code.equals(node.code)) return false;
        if (name != null ? !name.equals(node.name) : node.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
