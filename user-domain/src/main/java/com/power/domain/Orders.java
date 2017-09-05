





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-08-28 15:45:03 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;

import java.util.*;

public class Orders extends BaseDomain<Long>{
    /** 剩余费用(从充值费用中遵守先进先出的原则扣费剩余金额) */
    private Double surplusFee;
    /** 费用 */
    private Double totalFee;
    /** 用户ID，订单所属账户ID */
    private Long orderOwner;
    /** 服务商ID */
    private Long agency;
    /** 类型：充值BALANCE：消费CONSUME：充押金DEPOSIT：退押金REFUND */
    private String type;
    /** 创建时间 */
    private Date createDt;
    /** 更新时间 */
    private Date updateDt;
    /** 创建人 */
    private Long createBy;
    /** 更新时间 */
    private Long updateBy;
    /** 订单编号 */
    private String orderNo;
    /** 订单编号 */
    private String status;
    /** 父订单编号，退押金时使用 */
    private Long parentOrder;
    /** 退款订单列表  只有总退款订单会生成该属性 */
    private String parentOrderList;
    /** 交易类型，微信JSAPI，安卓苹果应用APP */
    private String tradeType;
    /**  */
    private String tradeIdentify;
    /**  */
    private Long editor;
    /**  */
    private String des;
    /**  */
    private String editorName;
    /** 计费模式 */
    private Long userRoles;

	public Orders(){
	}
    public void setSurplusFee(Double value) {
        this.surplusFee = value;
    }

    public Double getSurplusFee() {
        return this.surplusFee;
    }
    public void setTotalFee(Double value) {
        this.totalFee = value;
    }

    public Double getTotalFee() {
        return this.totalFee;
    }
    public void setOrderOwner(Long value) {
        this.orderOwner = value;
    }

    public Long getOrderOwner() {
        return this.orderOwner;
    }
    public void setAgency(Long value) {
        this.agency = value;
    }

    public Long getAgency() {
        return this.agency;
    }
    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return this.type;
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
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    public String getOrderNo() {
        return this.orderNo;
    }
    public void setParentOrder(Long value) {
        this.parentOrder = value;
    }

    public Long getParentOrder() {
        return this.parentOrder;
    }
    public void setParentOrderList(String value) {
        this.parentOrderList = value;
    }

    public String getParentOrderList() {
        return this.parentOrderList;
    }
    public void setTradeType(String value) {
        this.tradeType = value;
    }

    public String getTradeType() {
        return this.tradeType;
    }
    public void setTradeIdentify(String value) {
        this.tradeIdentify = value;
    }

    public String getTradeIdentify() {
        return this.tradeIdentify;
    }
    public void setEditor(Long value) {
        this.editor = value;
    }

    public Long getEditor() {
        return this.editor;
    }
    public void setDes(String value) {
        this.des = value;
    }

    public String getDes() {
        return this.des;
    }
    public void setEditorName(String value) {
        this.editorName = value;
    }

    public String getEditorName() {
        return this.editorName;
    }
    public void setUserRoles(Long value) {
        this.userRoles = value;
    }

    public Long getUserRoles() {
        return this.userRoles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SurplusFee",getSurplusFee())
			.append("TotalFee",getTotalFee())
			.append("OrderOwner",getOrderOwner())
			.append("Agency",getAgency())
			.append("Type",getType())
			.append("Status",getStatus())
			.append("CreateDt",getCreateDt())
			.append("UpdateDt",getUpdateDt())
			.append("CreateBy",getCreateBy())
			.append("UpdateBy",getUpdateBy())
			.append("OrderNo",getOrderNo())
			.append("ParentOrder",getParentOrder())
			.append("ParentOrderList",getParentOrderList())
			.append("TradeType",getTradeType())
			.append("TradeIdentify",getTradeIdentify())
			.append("Editor",getEditor())
			.append("Des",getDes())
			.append("EditorName",getEditorName())
			.append("UserRoles",getUserRoles())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Orders == false) return false;
		if(this == obj) return true;
		Orders other = (Orders)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

