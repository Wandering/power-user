





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:55 $ 
 */
package com.power.domain;
import com.power.core.domain.BaseDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class User extends BaseDomain<Long> {
    /** 用户唯一标识 */
    private String accountNo;
    /** 手机 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 预设用户名 */
    private String username;
    /** 预设密码 */
    private String password;
    /** 创建时间 */
    private Long createDt;
    /** 更新时间 */
    private Long updateDt;

	public User(){
	}
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    public String getAccountNo() {
        return this.accountNo;
    }
    public void setPhone(String value) {
        this.phone = value;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }
    public void setUsername(String value) {
        this.username = value;
    }

    public String getUsername() {
        return this.username;
    }
    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }
    public void setCreateDt(Long value) {
        this.createDt = value;
    }

    public Long getCreateDt() {
        return this.createDt;
    }
    public void setUpdateDt(Long value) {
        this.updateDt = value;
    }

    public Long getUpdateDt() {
        return this.updateDt;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AccountNo",getAccountNo())
			.append("Phone",getPhone())
			.append("Email",getEmail())
			.append("Username",getUsername())
			.append("Password",getPassword())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

