





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-14 20:09:24 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;;

import java.util.*;

public class UserExpand extends BaseDomain<Long>{
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String headimgurl;
    /** 性别 */
    private Integer sex;
    /** 城市 */
    private String city;
    /** 国家 */
    private String country;
    /** 省份 */
    private String province;
    /** 微信号 */
    private String weixin;
    /** 运营商ID */
    private Long agencyId;
    /** 用户ID */
    private Long userId;

	public UserExpand(){
	}
    public void setNickname(String value) {
        this.nickname = value;
    }

    public String getNickname() {
        return this.nickname;
    }
    public void setHeadimgurl(String value) {
        this.headimgurl = value;
    }

    public String getHeadimgurl() {
        return this.headimgurl;
    }
    public void setSex(Integer value) {
        this.sex = value;
    }

    public Integer getSex() {
        return this.sex;
    }
    public void setCity(String value) {
        this.city = value;
    }

    public String getCity() {
        return this.city;
    }
    public void setCountry(String value) {
        this.country = value;
    }

    public String getCountry() {
        return this.country;
    }
    public void setProvince(String value) {
        this.province = value;
    }

    public String getProvince() {
        return this.province;
    }
    public void setWeixin(String value) {
        this.weixin = value;
    }

    public String getWeixin() {
        return this.weixin;
    }
    public void setAgencyId(Long value) {
        this.agencyId = value;
    }

    public Long getAgencyId() {
        return this.agencyId;
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
			.append("Nickname",getNickname())
			.append("Headimgurl",getHeadimgurl())
			.append("Sex",getSex())
			.append("City",getCity())
			.append("Country",getCountry())
			.append("Province",getProvince())
			.append("Weixin",getWeixin())
			.append("AgencyId",getAgencyId())
			.append("UserId",getUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserExpand == false) return false;
		if(this == obj) return true;
		UserExpand other = (UserExpand)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

