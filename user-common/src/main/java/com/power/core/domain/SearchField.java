package com.power.core.domain;


import com.power.core.domain.wrapper.BaseWrapper;

/**
 * 搜索操作
 * 
 * @author qyang
 * 
 */
public class SearchField extends BaseWrapper {
	/* 搜索字段名称 */
	private String field;

	/* 操作符 == > < */
	private String op;

	/* 值 */
	private Object data;

	public SearchField(String field, String op, Object data)
	{
		this.field = field;
		this.op = op;
		this.data = data;

	}

	public SearchField()
	{

	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
