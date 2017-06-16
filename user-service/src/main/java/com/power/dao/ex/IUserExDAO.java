


/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:55 $ 
 */
package com.power.dao.ex;


import com.power.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

public interface IUserExDAO{
    UserInfoDTO getUserInfoByAccount(@Param("accountId") Long accountId,@Param("type")Integer type);
}
