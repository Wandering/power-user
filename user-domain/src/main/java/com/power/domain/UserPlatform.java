





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-09-11 10:14:40 $ 
 */
package com.power.domain;
import com.power.core.domain.BaseDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class UserPlatform extends BaseDomain<Long> {
    /** 公众号/服务号id */
    private Long platformId;
    /** openId */
    private String openId;
    /** 唯一key */
    private String unionId;
    /** 更新时间 */
    private Long updateTime;
    /** 创建时间 */
    private Long createTime;
    /** 用户ID */
    private Long userId;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserPlatform(){
	}
    public void setPlatformId(Long value) {
        this.platformId = value;
    }

    public Long getPlatformId() {
        return this.platformId;
    }
    public void setOpenId(String value) {
        this.openId = value;
    }

    public String getOpenId() {
        return this.openId;
    }
    public void setUnionId(String value) {
        this.unionId = value;
    }

    public String getUnionId() {
        return this.unionId;
    }
    public void setUpdateTime(Long value) {
        this.updateTime = value;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }
    public void setCreateTime(Long value) {
        this.createTime = value;
    }

    public Long getCreateTime() {
        return this.createTime;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PlatformId",getPlatformId())
			.append("OpenId",getOpenId())
			.append("UnionId",getUnionId())
			.append("UpdateTime",getUpdateTime())
			.append("CreateTime",getCreateTime())
			.append("UserId",getUserId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserPlatform == false) return false;
		if(this == obj) return true;
		UserPlatform other = (UserPlatform)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

