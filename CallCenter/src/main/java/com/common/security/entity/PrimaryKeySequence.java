package com.common.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: xqingli
 * Date: 1/29/13
 * Time: 10:15 PM
 */
@Entity
@Table(name = "tblprimarykeysequence")
public class PrimaryKeySequence {

    @Id
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldordersequence")
    private Long orderSequence;

    public PrimaryKeySequence() {}

    public PrimaryKeySequence(Integer id, Long orderSequence) {
        this.id = id;
        this.orderSequence = orderSequence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(Long orderSequence) {
        this.orderSequence = orderSequence;
    }
}
