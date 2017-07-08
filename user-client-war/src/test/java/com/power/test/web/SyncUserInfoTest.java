//package com.power.test.web;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Created by Administrator on 2017/7/7.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SyncUserInfoTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//
//    @Test
//    public void testSyncWxUser() throws Exception {
//
//        this.mvc.perform(get("/user/wechat/info/sync/ppower/syncWxUser").accept(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//        ;
//    }
//}
