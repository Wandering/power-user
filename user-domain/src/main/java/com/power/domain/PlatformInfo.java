





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-10 16:53:07 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;;

public class PlatformInfo extends BaseDomain<Long>{
    /** 生活号/服务号ID */
    private String appId;
    /** 密钥 */
    private String secret;
    /** 1:微信公众号2支付宝服务窗 */
    private Integer type;
    /** 开发者微信号 */
    private String adminUser;
    /** 微信token */
    private String token;
    /** aesKey */
    private String aesKey;
    /** 全局唯一公众号key */
    private String uniqueKey;
    /** 备注 */
    private String note;
    /** 运营商id */
    private Long agencyId;

	public PlatformInfo(){
	}
    public void setAppId(String value) {
        this.appId = value;
    }

    public String getAppId() {
        return this.appId;
    }
    public void setSecret(String value) {
        this.secret = value;
    }

    public String getSecret() {
        return this.secret;
    }
    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
    }
    public void setAdminUser(String value) {
        this.adminUser = value;
    }

    public String getAdminUser() {
        return this.adminUser;
    }
    public void setToken(String value) {
        this.token = value;
    }

    public String getToken() {
        return this.token;
    }
    public void setAesKey(String value) {
        this.aesKey = value;
    }

    public String getAesKey() {
        return this.aesKey;
    }
    public void setUniqueKey(String value) {
        this.uniqueKey = value;
    }

    public String getUniqueKey() {
        return this.uniqueKey;
    }
    public void setNote(String value) {
        this.note = value;
    }

    public String getNote() {
        return this.note;
    }
    public void setAgencyId(Long value) {
        this.agencyId = value;
    }

    public Long getAgencyId() {
        return this.agencyId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AppId",getAppId())
			.append("Secret",getSecret())
			.append("Type",getType())
			.append("AdminUser",getAdminUser())
			.append("Token",getToken())
			.append("AesKey",getAesKey())
			.append("UniqueKey",getUniqueKey())
			.append("Note",getNote())
			.append("AgencyId",getAgencyId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PlatformInfo == false) return false;
		if(this == obj) return true;
		PlatformInfo other = (PlatformInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

