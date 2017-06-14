





/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-14 22:13:09 $ 
 */
package com.power.dto;

import com.power.domain.PlatformInfo;
import com.power.domain.UserPlatform;


public class UserPlatformDTO extends PlatformInfo {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

