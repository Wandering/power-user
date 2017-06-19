package com.power.context;

import com.power.dto.UserInfoDTO;

/**
 * 用户上下文
 * <p/>
 * 创建时间: 14-9-1 下午11:21<br/>
 *
 * @author qyang
 * @since v0.0.1
 * @author Michael
 * @since v0.0.10
 */
public class UserContext {
    private static ThreadLocal<UserInfoDTO> context = new ThreadLocal<UserInfoDTO>();

    public static UserInfoDTO getCurrentUser(){
        return context.get();
    }

    public static void setCurrentUser(UserInfoDTO user){
        context.set(user);
    }

    public static void removeCurrentUser()
    {
        context.remove();
    }
}
