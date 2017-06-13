package com.power.core.service;

import java.util.Map;

/**
 * 持久化提供者接口,用于进行 CRU 操作  只需要重载  create(map) edit(map)
 * 其操作 默认 委托给  getMainService() 实际实现
 * <p/>
 * 创建时间: 16/1/10 上午11:09<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IPersistenceProvider{
    /**
     * 获取 主service
     * @return
     */
    public IBaseService getMainService();

    /**
     * 数据表单校验扩展
     * @param dataMap
     */
    void verifyData(Map<String, Object> dataMap);

    public Object create(Map<String, Object> entityMap);

    public int edit(Map<String, Object> entityMap);
}
