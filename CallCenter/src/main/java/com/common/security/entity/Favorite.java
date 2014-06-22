package com.common.security.entity;

import com.common.core.annotation.Comment;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Table(name = "TBLFAVORITE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Comment(value = "收藏")
public class Favorite {
    private Integer id;

    @javax.persistence.Column(name = "FLDID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Node node;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FLDCODE")
    public Node getNode(){
        return node;
    }

    public void setNode(Node node){
        this.node = node;
    }

    private String nodeCode;

    @javax.persistence.Column(name = "FLDCODE",insertable = false, updatable = false)
    @Basic
    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    private Integer userId;

    @javax.persistence.Column(name = "FLDUSERID")
    @Basic
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Date addTime;

    @javax.persistence.Column(name = "FLDADDTIME")
    @Basic
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    private String comment;

    @javax.persistence.Column(name = "FLDCOMMENT")
    @Basic
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
    public String getTitle(){
        if(node == null){
            return "";
        }
        return node.getName();
    }

    @Transient
    public String getUrl(){
        if(node == null){
            return "";
        }
        return node.getLink();
    }

    @Transient
    public String getIcon(){
        if(node == null){
            return "";
        }
        return node.getIcon();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Favorite favorite = (Favorite) o;

        if (addTime != null ? !addTime.equals(favorite.addTime) : favorite.addTime != null) return false;
        if (comment != null ? !comment.equals(favorite.comment) : favorite.comment != null) return false;
        if (id != null ? !id.equals(favorite.id) : favorite.id != null) return false;
        if (nodeCode != null ? !nodeCode.equals(favorite.nodeCode) : favorite.nodeCode != null) return false;
        if (userId != null ? !userId.equals(favorite.userId) : favorite.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nodeCode != null ? nodeCode.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
