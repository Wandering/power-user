package com.power.core.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * 基类 BaseDomain 只有一个ID属性,并且 ID 支持泛型
 * <p/>
 * 创建时间: 2014年4月17日 下午2:06:32 <br/>
 *
 * @author qyang
 * @version 
 * @since v0.0.1
 */
public class BaseDomain<T> implements Serializable {

    protected static final long serialVersionUID = 6667600871541308622L;
    // ID
    private T              id;

    /** 扩展属性，比较长用于 解决页面展示层 携带一个非主实体的属性；  */
    private Map<String, Object> extProps;

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Map<String, Object> getExtProps() {
        return extProps;
    }

    public void setExtProps(Map<String, Object> extProps) {
        this.extProps = extProps;
    }
}
