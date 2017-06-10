package com.power.core.dao;


import com.power.core.domain.BaseDomain;

import java.io.Serializable;

/**
 * DAO 基类   ,  如果IBaseDAO不满足特殊的场景,可以从这里继承(这样会保证任何基于IDAO接口的增强机制你都可以享有)
 * <p>
 */
public interface IDAO<T extends BaseDomain<ID>, ID extends Serializable> {
}
