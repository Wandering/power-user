package com.power.wechat.listener.event;

import com.power.dto.WxEvent;
import com.power.facade.IPlatformInfoFacade;
import com.power.facade.IUserAccountFacade;
import com.power.facade.IUserPlatformFacade;
import com.power.yuneng.activity.api.IActivityNotify;
import com.power.yuneng.activity.entity.dto.UserActivityExDTO;
import com.power.yuneng.user.IArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class LastSendEvent implements Observer{
    private static final Logger logger = LoggerFactory.getLogger(LastSendEvent.class);
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IActivityNotify activityNotify;
    @Autowired
    private IPlatformInfoFacade platformInfoFacade;
    @Autowired
    private IUserPlatformFacade userPlatformFacade;
    @Autowired
    private IUserAccountFacade userAccountFacade;
    public LastSendEvent(){}
    @Override
    public void update(Observable o, Object arg) {
//        WxEvent wxEvent = (WxEvent) arg;
//        Activity activity = activityNotify.getActivityById(1,wxEvent.getUniqueKey());
//        UserActivityExDTO userActivity = new UserActivityExDTO();
//        userActivity.setOpenId(wxEvent.getOpenId());
//        userActivity.setUniqueKey(wxEvent.getUniqueKey());
//        userActivity.setActivityId(1);
//        userActivity.setQuestionnaireId(1);
//        PlatformInfo platformInfo = platformInfoFacade.getPlatformInfoByUniqueKey(wxEvent.getUniqueKey());
//        UserPlatform userPlatform = userPlatformFacade.getWxPlatformByOpIdAndPid(wxEvent.getOpenId(),platformInfo.getAgencyId());
//        UserAccount userAccount = userAccountFacade.queryUserAccount(userPlatform.getUserId(),platformInfo.getAgencyId());
//        userActivity.setUserId(userAccount.getId());
//        logger.info("推送还电结束之后问卷消息");
//        if (activityNotify.hasGiveBonuses(userActivity)) {
//            logger.info("推送成功");
//            articleService.sendArticle(wxEvent.getUniqueKey(), wxEvent.getOpenId(), activity.getName(), activity.getActivityDesc(), activity.getImageUrl(), activity.getUrl());
//        }
    }
}
