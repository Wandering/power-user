package com.power.dao.ex;

import com.power.domain.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
public interface IUserAccountExDAO {
    UserAccount queryUserByOpenId(@Param("openId") String openId, @Param("uniqueKey")String uniqueKey);
}
