package com.common.security.entity;

import com.common.core.annotation.Comment;
import com.common.core.entity.Auditable;
import com.common.core.util.JsonTimestampSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: Allen
 * Date: 9/2/12
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "tbluser")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Comment(value = "用户")
public class User implements Auditable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fldid")
    private Integer id;

    @Column(name = "fldloginname")
    private String loginName;

    @Column(name = "fldusername")
    private String userName;

    @Column(name = "fldpassword")
    private String password;

    @Column(name = "fldloginstatus")
    private Integer loginStatus;

    @Column(name = "flduserstatus")
    private Integer userStatus;

    @Column(name = "fldloginerrcount")
    private Integer loginErrCount;

    @Column(name = "fldmodifypwdcount")
    private Integer modifyPwdCount;

    @Column(name = "fldgender")
    private Integer gender;

    @Column(name = "fldemail")
    private String email;

    @Column(name = "fldphone")
    private String phone;

    @Column(name = "fldmobile")
    private String mobile;

    @Column(name = "fldfax")
    private String fax;

    @Column(name = "fldaddress")
    private String address;

    @Column(name = "fldposition")
    private Integer position;

    @Column(name = "fldoperateid")
    private Integer operateId;

    @Column(name = "fldoperatedate")
    private Date operateDate;

    @Column(name = "fldcertcn")
    private String certCN;

    @JsonSerialize(using=JsonTimestampSerializer.class)
    @Column(name = "fldlastlogintime")
    private Date lastLoginTime;

    @Column(name = "fldgeneratedate")
    private Date generateDate;

    @Column(name = "fldsystem")
    private String system;

    @Column(name = "fldphoneextension")
    private String phoneExtension;

    @Column(name = "fldtype")
    private String type;

    @Column(name = "fldphonetype")
    private String phoneType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fldloginname", referencedColumnName = "fldloginname", insertable = false, updatable = false)
    private UserDept userDept;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<UserRole> userRole;

    public User() {
    }

    public User(Integer id, String loginName, String userName, String password, Integer loginStatus, Integer userStatus, Integer loginErrCount, Integer modifyPwdCount, Integer gender, String email, String phone, String mobile, String fax, String address, Integer position, Integer operateId, Date operateDate) {
        this.id = id;
        this.loginName = loginName;
        this.userName = userName;
        this.password = password;
        this.loginStatus = loginStatus;
        this.userStatus = userStatus;
        this.loginErrCount = loginErrCount;
        this.modifyPwdCount = modifyPwdCount;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.fax = fax;
        this.address = address;
        this.position = position;
        this.operateId = operateId;
        this.operateDate = operateDate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getLoginErrCount() {
        return loginErrCount;
    }

    public void setLoginErrCount(Integer loginErrCount) {
        this.loginErrCount = loginErrCount;
    }

    public Integer getModifyPwdCount() {
        return modifyPwdCount;
    }

    public void setModifyPwdCount(Integer modifyPwdCount) {
        this.modifyPwdCount = modifyPwdCount;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public UserDept getUserDept() {
        return userDept;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public void setUserDept(UserDept userDept) {
        this.userDept = userDept;
    }

    public String getDeptCode() {
        if (userDept == null)
            return null;
        return userDept.getDept().getDeptCode();
    }

    public String getDeptName() {
        if (userDept == null)
            return null;
        return userDept.getDept().getDeptName();
    }

    public String getCertCN() {
        return certCN;
    }

    public void setCertCN(String certCN) {
        this.certCN = certCN;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Transient
    private String roleNames;

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
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

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!loginName.equals(user.loginName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + loginName.hashCode();
        return result;
    }
}
