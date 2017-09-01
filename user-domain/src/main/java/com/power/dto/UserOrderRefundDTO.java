package com.power.dto;

import com.power.domain.Orders;
import com.power.domain.UserOrderRefund;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public class UserOrderRefundDTO extends UserOrderRefund{
    /**
     * 用户信息
     */
    UserInfoDTO userInfoDTO;


    List<UserOrder> userOrders;

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public List<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public static class UserOrder{
        /**
         * 原始订单
         */
        private Orders originalOrder;
        /**
         * 退款订单
         */
        private Orders refundOrder;

        public Orders getOriginalOrder() {
            return originalOrder;
        }

        public void setOriginalOrder(Orders originalOrder) {
            this.originalOrder = originalOrder;
        }

        public Orders getRefundOrder() {
            return refundOrder;
        }

        public void setRefundOrder(Orders refundOrder) {
            this.refundOrder = refundOrder;
        }
    }
}
