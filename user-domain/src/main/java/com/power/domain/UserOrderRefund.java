





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:04 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;;

public class UserOrderRefund extends BaseDomain<Long>{
    /** userId 订单的用户 */
    private Long userId;
    /** 订单 */
    private Long orderId;
    /** 创造时间 */
    private Long createDt;

	public UserOrderRefund(){
	}
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setOrderId(Long value) {
        this.orderId = value;
    }

    public Long getOrderId() {
        return this.orderId;
    }
    public void setCreateDt(Long value) {
        this.createDt = value;
    }

    public Long getCreateDt() {
        return this.createDt;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("OrderId",getOrderId())
			.append("CreateDt",getCreateDt())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserOrderRefund == false) return false;
		if(this == obj) return true;
		UserOrderRefund other = (UserOrderRefund)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

