package com.power.dao.ex;

import com.power.domain.PlatformInfo;
import com.power.dto.UserPlatformDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface IUserPlatformExDAO {
    UserPlatformDTO getPlatformByOpenIdAndType(@Param("agencyId")Long agencyId, @Param("type")Integer type, @Param("openId")String openId);
}
