





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:46 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;

import java.util.*;

public class UserAcounts extends BaseDomain<Long>{
    /** 账户编号 */
    private String accountNo;
    /** 用户id */
    private Long userId;
    /** 余额(本金) */
    private Double balance;
    /** 赠送金 */
    private Double bouns;
    /** 充返 */
    private Double backBouns;
    /** 会员类型 */
    private Long roles;
    /** 信用分 */
    private Long credit;
    /** 创建人 */
    private Date createDt;
    /** 修改时间 */
    private Date updateDt;
    /** 创建者 */
    private Long createBy;
    /** 修改者 */
    private Long updateBy;
    /** 押金 */
    private Double deposit;

	public UserAcounts(){
	}
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    public String getAccountNo() {
        return this.accountNo;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setBalance(Double value) {
        this.balance = value;
    }

    public Double getBalance() {
        return this.balance;
    }
    public void setBouns(Double value) {
        this.bouns = value;
    }

    public Double getBouns() {
        return this.bouns;
    }
    public void setBackBouns(Double value) {
        this.backBouns = value;
    }

    public Double getBackBouns() {
        return this.backBouns;
    }
    public void setRoles(Long value) {
        this.roles = value;
    }

    public Long getRoles() {
        return this.roles;
    }
    public void setCredit(Long value) {
        this.credit = value;
    }

    public Long getCredit() {
        return this.credit;
    }

    public void setCreateDt(Date value) {
        this.createDt = value;
    }

    public Date getCreateDt() {
        return this.createDt;
    }

    public void setUpdateDt(Date value) {
        this.updateDt = value;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }
    public void setCreateBy(Long value) {
        this.createBy = value;
    }

    public Long getCreateBy() {
        return this.createBy;
    }
    public void setUpdateBy(Long value) {
        this.updateBy = value;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }
    public void setDeposit(Double value) {
        this.deposit = value;
    }

    public Double getDeposit() {
        return this.deposit;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AccountNo",getAccountNo())
			.append("UserId",getUserId())
			.append("Balance",getBalance())
			.append("Bouns",getBouns())
			.append("BackBouns",getBackBouns())
			.append("Roles",getRoles())
			.append("Credit",getCredit())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.append("CreateBy",getCreateBy())
			.append("UpdateBy",getUpdateBy())
			.append("Deposit",getDeposit())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserAcounts == false) return false;
		if(this == obj) return true;
		UserAcounts other = (UserAcounts)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

