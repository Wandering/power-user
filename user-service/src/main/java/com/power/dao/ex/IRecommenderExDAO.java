package com.power.dao.ex;

import com.power.domain.UserRecommend;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/13.
 */
public interface IRecommenderExDAO {
    /**
     * 根据桩查询该桩的直接代理商，上级代理商
     * @param code 桩编号
     * @return id:1,parentId:2
     */
    Map<String,Object> getAgentAndParentByCode(String code);

    /**
     * 根据代理商ID获取代理商父ID 宇能的父ID是宇能
     * @param id 代理商ID
     * @return 父ID
     */
    long getAgentParentById(Long id);

    /**
     * 获取用户的父ID和类型
     * @param id 用户id
     * @return 直接推荐人/直接推荐人类型
     */
    UserRecommend getUserRecommendById(Long id);
}
