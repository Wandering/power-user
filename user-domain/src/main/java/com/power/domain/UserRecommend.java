





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-09-12 15:46:29 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;;

import java.util.*;

public class UserRecommend extends BaseDomain<Long>{
    /** 首次直接推荐人 */
    private Long directRecommender;
    /** 首次间接推荐人 */
    private Long indirectRecommender;
    /** 关注渠道 */
    private Integer channel;
    /** 推荐人类型:1:普通用户，2:代理商 */
    private Integer directType;
    /** 推荐人类型:1:普通用户，2:代理商 */
    private Integer indirectType;

	public UserRecommend(){
	}
    public void setDirectRecommender(Long value) {
        this.directRecommender = value;
    }

    public Long getDirectRecommender() {
        return this.directRecommender;
    }
    public void setIndirectRecommender(Long value) {
        this.indirectRecommender = value;
    }

    public Long getIndirectRecommender() {
        return this.indirectRecommender;
    }
    public void setChannel(Integer value) {
        this.channel = value;
    }

    public Integer getChannel() {
        return this.channel;
    }
    public void setDirectType(Integer value) {
        this.directType = value;
    }

    public Integer getDirectType() {
        return this.directType;
    }
    public void setIndirectType(Integer value) {
        this.indirectType = value;
    }

    public Integer getIndirectType() {
        return this.indirectType;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("DirectRecommender",getDirectRecommender())
			.append("IndirectRecommender",getIndirectRecommender())
			.append("Channel",getChannel())
			.append("DirectType",getDirectType())
			.append("IndirectType",getIndirectType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserRecommend == false) return false;
		if(this == obj) return true;
		UserRecommend other = (UserRecommend)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

