package com.power.wechat.service.impl;

import com.power.wechat.util.WxMpServiceUtil;
import com.power.yuneng.user.IQrCodeService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */
public class QrCodeServiceImpl implements IQrCodeService {
    @Override
    public String qrCodeCreateByStation(String stationCode,String uniqueKey) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        String rtnUrl = null;
        try {
            String longUrl = wxMpService.getQrcodeService().qrCodeCreateLastTicket(stationCode).getUrl();
            String shortUrl = wxMpService.shortUrl(longUrl);
            rtnUrl = shortUrl;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return rtnUrl + "?stationCode=" + stationCode;
    }

    @Override
    public List<String> qrCodeCreateByStation(List<String> stationCodes,String uniqueKey) {
        List<String> urls = new ArrayList<>();
        Iterator<String> iterator = stationCodes.iterator();
        while (iterator.hasNext()){
            urls.add(qrCodeCreateByStation(iterator.next(),uniqueKey));
        }
        return urls;
    }


    @Override
    public String qrCodeCreateByAgency(Long agencyId,String uniqueKey) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        try {
            return wxMpService.getQrcodeService().qrCodeCreateLastTicket("A"+agencyId).getUrl();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 换取普通用户30天二维码   形式为：http://xxxxxxxxx.xx/xxxxxxxx/xxxxxx
     * 二维码携带用户accountId，不是微信换取的短链接
     * 无开头字母
     *
     * @param accountId
     * @param uniqueKey
     * @return
     */
    @Override
    public String qrCodeCreateByUser(Long accountId, String uniqueKey) {
        WxMpService wxMpService = WxMpServiceUtil.getWxMpService(uniqueKey);
        try {
            return wxMpService.getQrcodeService().qrCodeCreateTmpTicket(accountId.intValue(),2592000).getUrl();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
