





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:00 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;

import java.util.*;

public class OrderLine extends BaseDomain<Long>{
    /** 费用 */
    private Double fee;
    /** 用户ID */
    private Long userId;
    /** 服务商ID */
    private Long agency;
    /** 借出充电桩ID */
    private String fromStation;
    /** 归还充电桩ID */
    private String toStation;
    /** 充电宝ID */
    private String powerBank;
    /** 交易类型，BALANCE,CONSUME,DEPOSIT */
    private String feeType;
    /** 订单ID */
    private Long orderId;
    /** 创建时间 */
    private Date createDt;
    /**  */
    private Date updateDt;
    /** 创建者 */
    private Long createBy;
    /** 更新者 */
    private Long updateBy;
    /** 开始时间 */
    private Long startDt;
    /** 结束时间 */
    private Long endDt;

	public OrderLine(){
	}
    public void setFee(Double value) {
        this.fee = value;
    }

    public Double getFee() {
        return this.fee;
    }
    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setAgency(Long value) {
        this.agency = value;
    }

    public Long getAgency() {
        return this.agency;
    }
    public void setFromStation(String value) {
        this.fromStation = value;
    }

    public String getFromStation() {
        return this.fromStation;
    }
    public void setToStation(String value) {
        this.toStation = value;
    }

    public String getToStation() {
        return this.toStation;
    }
    public void setPowerBank(String value) {
        this.powerBank = value;
    }

    public String getPowerBank() {
        return this.powerBank;
    }
    public void setFeeType(String value) {
        this.feeType = value;
    }

    public String getFeeType() {
        return this.feeType;
    }
    public void setOrderId(Long value) {
        this.orderId = value;
    }

    public Long getOrderId() {
        return this.orderId;
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
    public void setStartDt(Long value) {
        this.startDt = value;
    }

    public Long getStartDt() {
        return this.startDt;
    }
    public void setEndDt(Long value) {
        this.endDt = value;
    }

    public Long getEndDt() {
        return this.endDt;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Fee",getFee())
			.append("UserId",getUserId())
			.append("Agency",getAgency())
			.append("FromStation",getFromStation())
			.append("ToStation",getToStation())
			.append("PowerBank",getPowerBank())
			.append("FeeType",getFeeType())
			.append("OrderId",getOrderId())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.append("CreateBy",getCreateBy())
			.append("UpdateBy",getUpdateBy())
			.append("StartDt",getStartDt())
			.append("EndDt",getEndDt())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrderLine == false) return false;
		if(this == obj) return true;
		OrderLine other = (OrderLine)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

