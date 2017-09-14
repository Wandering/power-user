package com.power.service.ex.impl;

import com.power.dao.ex.IRecommenderExDAO;
import com.power.domain.UserRecommend;
import com.power.service.ex.IRecommenderExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 杨永平 on 2017/9/13.
 */
@Service("RecommenderExServiceImpl")
public class RecommenderExServiceImpl implements IRecommenderExService {
    @Autowired
    IRecommenderExDAO recommenderExDAO;
    /**
     * 根据桩查询该桩的直接代理商，上级代理商
     *
     * @param code 桩编号
     * @return id:1,parentId:2
     */
    @Override
    public Map<String, Object> getAgentAndParentByCode(String code) {
        return recommenderExDAO.getAgentAndParentByCode(code);
    }

    /**
     * 根据代理商ID获取代理商父ID 宇能的父ID是宇能
     *
     * @param id 代理商ID
     * @return 父ID
     */
    @Override
    public long getAgentParentById(Long id) {
        return recommenderExDAO.getAgentParentById(id);
    }

    /**
     * 获取用户的父ID和类型
     *
     * @param id 用户id
     * @return 直接推荐人/直接推荐人类型
     */
    @Override
    public UserRecommend getUserRecommendById(Long id) {
        return recommenderExDAO.getUserRecommendById(id);
    }
}
