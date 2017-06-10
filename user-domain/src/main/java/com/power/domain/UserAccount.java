





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:56 $ 
 */
package com.power.domain;
import com.power.core.domain.BaseDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class UserAccount extends BaseDomain<Long> {
    /** 用户ID */
    private Long userId;
    /** 运营商Id */
    private Integer agencyId;
    /** 创建时间 */
    private Date createDt;
    /** 更新时间 */
    private Date updateDt;
    /** 创建者 */
    private Long createBy;
    /** 更改时间 */
    private Long updateBy;

	public UserAccount(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setAgencyId(Integer value) {
        this.agencyId = value;
    }

    public Integer getAgencyId() {
        return this.agencyId;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("AgencyId",getAgencyId())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.append("CreateBy",getCreateBy())
			.append("UpdateBy",getUpdateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserAccount == false) return false;
		if(this == obj) return true;
		UserAccount other = (UserAccount)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

