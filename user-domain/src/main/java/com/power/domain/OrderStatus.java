package com.power.domain;

public enum OrderStatus {
	PENDING, //进行中
	ACTIVE,//激活
	PRE_PAY,
	PART_PAYED, //部分支付
	PAYED,//支付完成
	IN_COUNT,
	REFUND,//退款
	REFUND_FAIL,//退款
	REFUND_BALANCE,//退款
	PRE_BORROW,//5.23新加预订单
	PENDING_PAYED//6.14 新加 未归还但已结算订单
	;
}
