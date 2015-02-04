package com.phone.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by thinkpad on 15-1-18.
 */
@Entity
public class StatRecordValue implements Serializable {

    private static final long serialVersionUID = 7072941603276981091L;
    private Integer id;
    private Integer statusValue;
    private String statusName;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "StatusValue")
    public Integer getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
        this.statusValue = statusValue;
    }

    @Basic
    @Column(name = "StatusName")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatRecordValue that = (StatRecordValue) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (statusName != null ? !statusName.equals(that.statusName) : that.statusName != null) return false;
        if (statusValue != null ? !statusValue.equals(that.statusValue) : that.statusValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (statusValue != null ? statusValue.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        return result;
    }
}
