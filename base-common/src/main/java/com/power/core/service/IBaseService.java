package com.power.core.service;


import com.power.core.dao.IBaseDAO;
import com.power.core.domain.BaseDomain;
import com.power.core.domain.Sorter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Service接口基类 注入DAO完成数据库访问操作, 主要无业务语义的方法
 * <p/>
 * 创建时间: 14-9-5 上午10:27<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IBaseService<ID extends Serializable, D extends IBaseDAO,T extends BaseDomain> extends IPersistenceProvider{

    public D getDao();

    /**
     * 新增一条数据
     *
     * @param entity
     */
    public ID create(T entity);
//    public ID create(Map<String, Object> entityMap);

    /**
     * 修改一条数据
     *
     * @param entity
     *            要更新的实体对象
     */
    public int edit(T entity);
//    public int edit(Map<String, Object> entityMap);
    public int editByCondition(Map<String, Object> updateMap, Map<String, Object> conditionMap);
    public int editByWhereSql(Map<String, Object> updateMap, String nativeSql);
    public int editNull(T entity);

    /**
     * 删除一条数据
     *
     * @param id
     */
    public void delete(ID id);
    public void delete(List<ID> ids);
    public void deleteAll();
    public void deleteByCondition(Map<String, Object> condition);
    public void deleteByWhereSql(String nativeSql);

    /**
     * 查看一条确定的数据
     *
     * @param id
     * @return
     */
    public T view(ID id);

    public T viewOne(Map<String, Object> selector, Map<String, Object> condition, List<Sorter> sorter);
    public T viewOne(Map<String, Object> condition);

    public T viewOneByWhereSql(Map<String, Object> selector, String nativeSql, List<Sorter> sorter);
    public List<T> viewList(Map<String, Object> selector, Map<String, Object> condition, List<Sorter> sorter);
    public List<T> viewList(Map<String, Object> condition);
    public List<T> viewListByWhereSql(Map<String, Object> selector, String nativeSql, List<Sorter> sorter);



}
