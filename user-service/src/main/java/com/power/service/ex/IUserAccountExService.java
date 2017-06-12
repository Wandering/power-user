/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:56 $ 
 */



package com.power.service.ex;


import com.power.domain.UserAccount;

import java.util.Map;

public interface IUserAccountExService{
    UserAccount queryUserByOpenId(String openId, String uniqueKey);
}
