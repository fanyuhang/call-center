package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;
import com.common.core.util.JsonTimestampSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Allen
 * Date: 11/4/12
 */
@Entity
@Table(name = "tblcertactive")
@Comment(value = "客户证书")
public class CertActive implements Auditable, Serializable {

    @Id
    @Column(name = "fldcertcn")
    private String certCN;

    @Column(name = "fldpublickey")
    private String publicKey;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    @Column(name = "fldgeneratedate")
    private Date generateDate;

    @Column(name = "fldsystem")
    private String system;

    public CertActive() {
    }

    public CertActive(String certCN, String publicKey) {
        this.certCN = certCN;
        this.publicKey = publicKey;
    }

    public String getCertCN() {
        return certCN;
    }

    public void setCertCN(String certCN) {
        this.certCN = certCN;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    @JsonSerialize(using = JsonTimestampSerializer.class)
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
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

        CertActive that = (CertActive) o;

        if (certCN != null ? !certCN.equals(that.certCN) : that.certCN != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return certCN != null ? certCN.hashCode() : 0;
    }
}
