package com.power.dto;

import com.google.common.collect.Maps;
import com.power.domain.UserAccount;
import com.power.domain.UserPlatform;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */
public class UserInfoDTO {
    /**用户全系统ID**/
    private Long userId;
    /**用户业务系统Key为业务系统的平台id**/
    Map<String,UserPlatform> userPlatformMap = Maps.newConcurrentMap();
    /**用户所属运营商信息Key为业务系统的平台id**/
    Map<String,UserAccount> userAccountMap = Maps.newConcurrentMap();
    /**昵称**/
    private String nickname;
    /**头像**/
    private String headimgurl;
    /**性别**/
    private String sex;
    /**城市**/
    private String city;
    /**国家**/
    private String country;
    /**省份**/
    private String province;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
