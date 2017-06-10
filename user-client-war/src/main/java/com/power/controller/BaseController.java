package com.power.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/8.
 */
@RestController
public class BaseController {




    @RequestMapping("test123")
    @ResponseBody
    public String test1(){
        return "1";
    }
}
