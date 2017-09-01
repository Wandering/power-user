package com.power.domain;

public enum OrderType {
	INVITE("邀请赠送"), DISPOSIT_BALANCE("充值押金和余额"),DEPOSIT("充电服务"), BALANCE("充值余额"), CONSUME("借用消费"),REFUND_BALANCE("退款消费"), REFUND("退款"),YEARFEE("充值会员年费");

	private String description;

	private OrderType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
