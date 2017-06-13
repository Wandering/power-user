package com.power.core.utils;


import com.alibaba.fastjson.serializer.PropertyFilter;
import com.power.core.protocol.RequestT;
import com.power.core.protocol.ResponseT;

/**
 * 序列化时判断StyleEnum，非PLAIN时过滤掉data/bizData
 * <p/>
 * 创建时间: 16/1/8<br/>
 *
 * @author xule
 * @since v0.0.1
 */
public class ExtPropertyFilter implements PropertyFilter {

    public static String[] filterParams = new String[]{"data", "bizData"};

    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object instanceof RequestT) {
            RequestT re = (RequestT) object;
            StyleEnum style = re.getStyle();
            if (!StyleEnum.PLAIN.equals(style) && contains(name)) {
                return false;
            }
        }
        if (object instanceof ResponseT) {
            ResponseT re = (ResponseT) object;
            StyleEnum style = re.getStyle();
            if (!StyleEnum.PLAIN.equals(style) && contains(name)) {
                return false;
            }
        }
        return true;
    }

    private boolean contains(String name){
        for(String filerParam : filterParams){
            if(filerParam.equals(name)){
                return true;
            }
        }
        return false;
    }

}
