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
    /**昵称**/
    private String nickname;
    /**头像**/
    private String headimgurl;
    /**性别**/
    private Integer sex;
    /**城市**/
    private String city;
    /**国家**/
    private String country;
    /**省份**/
    private String province;

    private Long agencyId;

    private Long accountId;

    private String phone;

    private String openId;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
