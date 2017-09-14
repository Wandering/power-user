





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-09-11 10:14:44 $ 
 */
package com.power.domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.power.core.domain.BaseDomain;

import java.util.*;

public class WxMsg extends BaseDomain<Integer>{
    /** 模板id */
    private String msgId;
    /** 模板名称标识 */
    private String msgName;
    /**  */
    private String msgTitle;
    /** 消息推送类型 TEXT */
    private String msgType;
    /** 消息模板内容 */
    private String contentModel;
    /** 微信公众号识别key */
    private String uniquekey;

	public WxMsg(){
	}
    public void setMsgId(String value) {
        this.msgId = value;
    }

    public String getMsgId() {
        return this.msgId;
    }
    public void setMsgName(String value) {
        this.msgName = value;
    }

    public String getMsgName() {
        return this.msgName;
    }
    public void setMsgTitle(String value) {
        this.msgTitle = value;
    }

    public String getMsgTitle() {
        return this.msgTitle;
    }
    public void setMsgType(String value) {
        this.msgType = value;
    }

    public String getMsgType() {
        return this.msgType;
    }
    public void setContentModel(String value) {
        this.contentModel = value;
    }

    public String getContentModel() {
        return this.contentModel;
    }
    public void setUniquekey(String value) {
        this.uniquekey = value;
    }

    public String getUniquekey() {
        return this.uniquekey;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MsgId",getMsgId())
			.append("MsgName",getMsgName())
			.append("MsgTitle",getMsgTitle())
			.append("MsgType",getMsgType())
			.append("ContentModel",getContentModel())
			.append("Uniquekey",getUniquekey())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WxMsg == false) return false;
		if(this == obj) return true;
		WxMsg other = (WxMsg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

