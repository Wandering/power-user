package com.power.core.restful;

import com.alibaba.fastjson.TypeReference;
import com.power.core.protocol.RequestT;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 14/11/30 上午11:16<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class RawTypeReference {
    public static TypeReference stringTypeReference = new TypeReference<RequestT<String>>(){};
    public static TypeReference booleanTypeReference = new TypeReference<RequestT<Boolean>>(){};
    public static TypeReference integerTypeReference = new TypeReference<RequestT<Integer>>(){};
    public static TypeReference longTypeReference = new TypeReference<RequestT<Long>>(){};
}
