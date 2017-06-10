/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 18:43:45 $ 
 */
package com.power.domain;
import com.power.core.domain.BaseDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

public class Agencies extends BaseDomain<Long> {
    /** 服务商名称 */
    private String name;
    /** 英文缩写???? */
    private String abbrCode;
    /** 地域 */
    private Long region;
    /** 地址 */
    private String address;
    /** 类型：平台还是服务商 */
    private String type;
    /** 父运营商 */
    private Long parent;
    /** 创建时间 */
    private Date createDt;
    /** 更新时间 */
    private Date updateDt;
    /** 创建人 */
    private Long createBy;
    /** 更新人 */
    private Long updateBy;

    private Integer status;



	public Agencies(){
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setAbbrCode(String value) {
        this.abbrCode = value;
    }

    public String getAbbrCode() {
        return this.abbrCode;
    }
    public void setRegion(Long value) {
        this.region = value;
    }

    public Long getRegion() {
        return this.region;
    }
    public void setAddress(String value) {
        this.address = value;
    }

    public String getAddress() {
        return this.address;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }
    public void setParent(Long value) {
        this.parent = value;
    }

    public Long getParent() {
        return this.parent;
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
			.append("Name",getName())
			.append("AbbrCode",getAbbrCode())
			.append("Region",getRegion())
			.append("Address",getAddress())
			.append("Status",getStatus())
			.append("Type",getType())
			.append("Parent",getParent())
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
		if(obj instanceof Agencies == false) return false;
		if(this == obj) return true;
        Agencies other = (Agencies)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

