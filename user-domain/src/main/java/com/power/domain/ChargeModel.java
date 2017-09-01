





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-29 09:01:43 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;;

import java.util.*;

public class ChargeModel extends BaseDomain<Long>{
    /** 会员模式名称 */
    private String name;
    /** 计费机制 0 24小时 1 自然日制 */
    private Boolean chargeDay;
    /** 首充金额 */
    private Double firstDeposit;
    /** 最小充值金额 */
    private Double minDeposit;
    /** 会员年费 */
    private Double yearFee;
    /** VIP累计专用 单位 min */
    private Integer freeTime;
    /** 超时费用 单位 分 */
    private Long overdueFee;
    /** 最大超时费用 单位 分 */
    private Long maxOverdueFee;
    /** 还电缓冲时间 单位 min */
    private Integer bufferTime;
    /** 创建时间 */
    private Date createDt;
    /** 更新时间 */
    private Date updateDt;
    /** 创建者 */
    private Long createBy;
    /** 修改者 */
    private Long updateBy;
    /** 扣费比例 */
    private Long borrowScale;
    /** 普通首免 单位 min */
    private Long orderFreeTime;
    /** 有效期 */
    private Date endTime;

	public ChargeModel(){
	}
    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }
    public void setChargeDay(Boolean value) {
        this.chargeDay = value;
    }

    public Boolean getChargeDay() {
        return this.chargeDay;
    }
    public void setFirstDeposit(Double value) {
        this.firstDeposit = value;
    }

    public Double getFirstDeposit() {
        return this.firstDeposit;
    }
    public void setMinDeposit(Double value) {
        this.minDeposit = value;
    }

    public Double getMinDeposit() {
        return this.minDeposit;
    }
    public void setYearFee(Double value) {
        this.yearFee = value;
    }

    public Double getYearFee() {
        return this.yearFee;
    }
    public void setFreeTime(Integer value) {
        this.freeTime = value;
    }

    public Integer getFreeTime() {
        return this.freeTime;
    }
    public void setOverdueFee(Long value) {
        this.overdueFee = value;
    }

    public Long getOverdueFee() {
        return this.overdueFee;
    }
    public void setMaxOverdueFee(Long value) {
        this.maxOverdueFee = value;
    }

    public Long getMaxOverdueFee() {
        return this.maxOverdueFee;
    }
    public void setBufferTime(Integer value) {
        this.bufferTime = value;
    }

    public Integer getBufferTime() {
        return this.bufferTime;
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
    public void setBorrowScale(Long value) {
        this.borrowScale = value;
    }

    public Long getBorrowScale() {
        return this.borrowScale;
    }
    public void setOrderFreeTime(Long value) {
        this.orderFreeTime = value;
    }

    public Long getOrderFreeTime() {
        return this.orderFreeTime;
    }

    public void setEndTime(Date value) {
        this.endTime = value;
    }

    public Date getEndTime() {
        return this.endTime;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("ChargeDay",getChargeDay())
			.append("FirstDeposit",getFirstDeposit())
			.append("MinDeposit",getMinDeposit())
			.append("YearFee",getYearFee())
			.append("FreeTime",getFreeTime())
			.append("OverdueFee",getOverdueFee())
			.append("MaxOverdueFee",getMaxOverdueFee())
			.append("BufferTime",getBufferTime())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.append("CreateBy",getCreateBy())
			.append("UpdateBy",getUpdateBy())
			.append("BorrowScale",getBorrowScale())
			.append("OrderFreeTime",getOrderFreeTime())
			.append("EndTime",getEndTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ChargeModel == false) return false;
		if(this == obj) return true;
		ChargeModel other = (ChargeModel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

