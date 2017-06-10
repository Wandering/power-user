/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-09 16:32:53 $ 
 */



package com.power.service.ex.impl;

import com.power.dao.IPlatformInfoDAO;
import com.power.service.ex.IPlatformInfoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformInfoExServiceImpl implements IPlatformInfoExService{

    @Autowired
    IPlatformInfoDAO platformInfoDAO;

}
