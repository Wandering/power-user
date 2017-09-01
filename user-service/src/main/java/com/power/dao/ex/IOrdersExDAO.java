package com.power.dao.ex;

import com.power.dto.UserPlatformDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface IOrdersExDAO {
    /**
     * 统计用户某种状态下的订单数量
     * @param accountId
     * @return
     */
    Integer countOrderByTypeAndStatus(Long accountId,String type,String status);

}
