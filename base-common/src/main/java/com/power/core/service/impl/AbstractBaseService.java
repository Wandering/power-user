package com.power.core.service.impl;

//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import com.google.common.collect.Maps;
import com.power.core.dao.IBaseDAO;
import com.power.core.domain.BaseDomain;
import com.power.core.domain.CreateBaseDomain;
import com.power.core.domain.SearchField;
import com.power.core.domain.Sorter;
import com.power.core.domain.wrapper.SearchEnum;
import com.power.core.service.IBaseService;
import com.power.core.service.IDaoAware;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 服务抽象类  TODO:记录操作日志
 * <p/>
 * 创建时间: 14-9-22 下午5:51<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
//@CacheConfig(cacheNames = {CacheConstants.ALLUNUSED})
public abstract class AbstractBaseService<ID extends Serializable, D extends IBaseDAO, T extends BaseDomain> implements IBaseService<ID,D, T>, IDaoAware<D, T> {
    @Override
    public void verifyData(Map<String, Object> dataMap) {

    }

    @Override
    public IBaseService getMainService() {
        return this;
    }

    @Override
    public ID create(T entity) {
        enhanceNewCreateBaseDomain(entity);
        return (ID) getDao().insert(entity);
    }

    @Override
    public ID create(Map<String, Object> entityMap) {
        enhanceNewCreateBaseDomain(entityMap);
        return (ID) getDao().insertMap(entityMap);
    }

    @Override
    public int edit(T entity) {
        enhanceCreateBaseDomain(entity);
        return getDao().update(entity);
    }

    @Override
    public int edit(Map<String, Object> entityMap) {
        enhanceCreateBaseDomain(entityMap);
        return getDao().updateMap(entityMap);
    }

    @Override
    public int editByCondition(Map<String, Object> updateMap, Map<String, Object> conditionMap) {
        enhanceCreateBaseDomain(updateMap);
        return getDao().updateByCondition(updateMap, conditionMap);
    }

    @Override
    public int editByWhereSql(Map<String, Object> updateMap, String nativeSql) {
        enhanceCreateBaseDomain(updateMap);
        return getDao().updateByWhereSql(updateMap, nativeSql);
    }

    @Override
    public void delete(ID id) {
        getDao().delete(id);
    }

    @Override
    public int editNull(T entity) {
        enhanceCreateBaseDomain(entity);
        return getDao().updateNull(entity);
    }

    @Override
    public void delete(List<ID> ids) {
        getDao().delete(ids);
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void deleteByCondition(Map<String, Object> condition) {
        getDao().deleteByCondition(condition);
    }

    @Override
    public void deleteByWhereSql(String nativeSql) {
        getDao().deleteByWhereSql(nativeSql);
    }

    @Override
    public T view(ID id) {
        return (T) getDao().findOne(id);
    }

    @Override
    public T viewOne(Map<String, Object> selector, Map<String, Object> condition, List<Sorter> sorter) {
        return (T) getDao().queryOne(selector, condition, sorter);
    }

    @Override
    public T viewOne(Map<String, Object> params) {
        return (T) getDao().queryOne(null, genQueryCondition(params), null);
    }

    @Override
    public T viewOneByWhereSql(Map<String, Object> selector, String nativeSql, List<Sorter> sorter) {
        return (T) getDao().queryOneByWhereSql(selector, nativeSql, sorter);
    }

    @Override
    public List<T> viewList(Map<String, Object> selector, Map<String, Object> condition, List<Sorter> sorter) {
        return getDao().queryList(selector, condition, sorter);
    }

    @Override
    public List<T> viewList(Map<String, Object> params) {

        return getDao().queryList(null, genQueryCondition(params), null);
    }

    @Override
    public List<T> viewListByWhereSql(Map<String, Object> selector, String nativeSql, List<Sorter> sorter) {
        return getDao().queryListByWhereSql(selector, nativeSql, sorter);
    }

    private final T enhanceCreateBaseDomain(T entity){
        if(entity instanceof CreateBaseDomain){
            ((CreateBaseDomain) entity).setLastModDate(System.currentTimeMillis());
            //TODO 当前用户
        }

        return entity;
    }

    private final T enhanceNewCreateBaseDomain(T entity){
        if(entity instanceof CreateBaseDomain){
            //设置默认值，如果默认值和common不一样，需要自行设置初始值
            if (((CreateBaseDomain) entity).getCreateDate() == null){
                ((CreateBaseDomain) entity).setCreateDate(System.currentTimeMillis());
            }
            if (((CreateBaseDomain) entity).getStatus() == null){
                ((CreateBaseDomain) entity).setStatus(0);
            }
            if (((CreateBaseDomain) entity).getLastModDate() == null){
                ((CreateBaseDomain) entity).setLastModDate(0l);
            }
            if (((CreateBaseDomain) entity).getCreator() == null){
                ((CreateBaseDomain) entity).setCreator(0l);
            }
            if (((CreateBaseDomain) entity).getLastModifier() == null){
                ((CreateBaseDomain) entity).setLastModifier(0l);
            }
            //TODO 当前用户
        }

        return entity;
    }

    private final Map enhanceCreateBaseDomain(Map entityMap){
        entityMap.put("lastModDate", System.currentTimeMillis());

        return entityMap;
    }

    private final Map enhanceNewCreateBaseDomain(Map entityMap){
        entityMap.put("createDate", System.currentTimeMillis());

        return entityMap;
    }

    private final Map genQueryCondition(Map<String, Object> params){
        Map<String,Object> condition = Maps.newHashMap();
        for (Map.Entry<String,Object> entry : params.entrySet()) {
            SearchField searchField = new SearchField();
            searchField.setField(entry.getKey());
            searchField.setData(entry.getValue());
            searchField.setOp(SearchEnum.eq.getValue());
            condition.put(entry.getKey(), searchField);
            condition.put("groupOp", "AND");
        }
        return condition;
    }
}
