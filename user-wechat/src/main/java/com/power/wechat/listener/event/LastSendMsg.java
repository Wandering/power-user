package com.power.wechat.listener.event;

import com.power.dto.WxEvent;
import com.power.enums.PowerEvent;
import com.power.wechat.listener.EventObservableFactory;
import com.power.yuneng.user.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class LastSendMsg implements Observer{
    @Autowired
    private IArticleService articleService;
    public LastSendMsg(){}
    @Override
    public void update(Observable o, Object arg) {
        WxEvent wxEvent = (WxEvent) arg;
//        String title,String desc, String picurl, String url
        articleService.sendArticle(wxEvent.getUniqueKey(),wxEvent.getOpenId(),"我只是个测试","我只是个测试","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2546541266,732148371&fm=173&s=C7E602E6126A875546C1BAB703002005&w=639&h=399&img.JPEG","https://www.baidu.com/home/news/data/newspage?nid=3118762423702707450&n_type=0&p_from=1&dtype=-1");
    }
}
