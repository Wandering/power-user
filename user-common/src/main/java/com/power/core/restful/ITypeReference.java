package com.power.core.restful;

import com.alibaba.fastjson.TypeReference;

/**
 * fastjson 泛型支持 接口
 * <p/>
 * 创建时间: 14/11/26 下午4:24<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface ITypeReference {
    /**
     * 根据url获取requestT的泛型实例
     * @param url
     * @return
     */
    public TypeReference getTypeReference(String url);
}
