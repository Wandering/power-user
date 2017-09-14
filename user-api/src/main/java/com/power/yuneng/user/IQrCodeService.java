package com.power.yuneng.user;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public interface IQrCodeService {

    /**
     * 换取充电桩永久URL,形式为：http://xxxxxxxxx.xx/xxxxxxxx/xxxxxx?stationCode=A00000001
     * 链接为从微信换取的短链接
     * 二维码中携带桩编号，在关注是可以获取到桩编号
     * @param uniqueKey
     * @return
     */
    String qrCodeCreateByStation(String stationCode,String uniqueKey);

    List<String> qrCodeCreateByStation(List<String> stationCodes,String uniqueKey);

    /**
     * 换取代理商永久二维码   形式为：http://xxxxxxxxx.xx/xxxxxxxx/xxxxxx
     * 二维码携带代理商id，不是微信换取的短链接
     * 以G开头
     * @param agencyId
     * @param uniqueKey
     * @return
     */
    String qrCodeCreateByAgency(Long agencyId,String uniqueKey);


    /**
     * 换取普通用户30天二维码   形式为：http://xxxxxxxxx.xx/xxxxxxxx/xxxxxx
     * 二维码携带用户accountId，不是微信换取的短链接
     * @param accountId
     * @param uniqueKey
     * @return
     */
    String qrCodeCreateByUser(Long accountId,String uniqueKey);
}
