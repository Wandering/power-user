package com.power.core.dao;

import com.power.core.domain.BaseDomain;
import com.power.core.domain.Sorter;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.CrudRepository;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 使用统一的接口定义 利于编码习惯统一
 * 因为使用autoscan,需要每个mapper.xml都有方法对应的声明
 */
public interface IBaseDAO<ID extends Serializable, T extends BaseDomain<ID>> extends IDAO<T, ID>, CrudRepository<T, ID> {
    //------------------------------ spring-data CrudRepository START ---------------------------//

    //------------------------------ C START ---------------------------//
    /**
     * 保存单一对象  如果采用集中式的ID生成,传入的entity ID属性有值;如果使用数据库的自增ID,传入的entity ID属性无值
     *
     * @param entity
     *
     * @return 主键ID
     */
    public ID insert(T entity);

    /**
     * map类型数据的新增
     * @param entityMap
     */
    public ID insertMap(@Param("map") Map<String, Object> entityMap);


    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    <S extends T> List<S> save(Iterable<S> entities);

    //------------------------------ C END ---------------------------//

    //------------------------------ U START ---------------------------//
    /**
     * 根据主键ID 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null，请参看{@link #updateNull(T)}
     *
     * @param entity 要更新的实体对象
     *
     * @return 大于0更新成功
     */
    public int update(T entity);

    /**
     * 根据主键ID 更新对象
     * @param entityMap 必须包含 key 为 "id" 的键值对
     * @return 大于0更新成功
     */
    public int updateMap(@Param("map") Map<String, Object> entityMap);

    /**
     * 通用的更新操作 通过 condition 过滤数据
     * @param updateMap 需要更新的值
     * @param conditionMap 需要被更新的条件  支持值的多种方式  = > < 等
     */
    public int updateByCondition(@Param("update") Map<String, Object> updateMap, @Param("condition") Map<String, Object> conditionMap);

    /**
     * 任意sql条件的 对象更新,  主要是满足多种复杂组合条件 例如: ((fieldA > ? or fieldB = ?) and (fieldC = ? or fieldD = ?))
     * @param updateMap
     * @param nativeSql 原生的 where 条件 sql片段
     * @return
     */
    public int updateByWhereSql(@Param("update") Map<String, Object> updateMap, @Param("nativeSql") String nativeSql);


    /**
     * 更新对象,如果属性为空，会进行对应的属性值更新,如果有属性不想更新为null，请参看{@link #update(T)}
     *
     * @param entity
     */
    public int updateNull(T entity);
    //------------------------------ U END ---------------------------//


    //------------------------------ R START ---------------------------//

    /** 根据ID\IDS获取 对象见 父接口定义 */

    /**
     * 根据条件集合进行指定类型单一对象查询  无论条件获取到的是几条 都用 limit 1 限制返回只有一条
     *
     * @param selector 需要查询的字段, null的话查询该表的所有字段
     * @param condition
     *            进行查询的条件集合
     * @return 返回泛型参数类型的对象，如何取到泛型类型参数，请参看{@link #getEntityClass()}，
     */
    public T queryOne(@Param("selector") Map<String, Object> selector, @Param("condition") Map<String, Object> condition, @Param("sorter") List<Sorter> sorter);
    public T queryOneByWhereSql(@Param("selector") Map<String, Object> selector, @Param("nativeSql") String nativeSql, @Param("sorter") List<Sorter> sorter);

    public List<T> queryList(@Param("selector") Map<String, Object> selector, @Param("condition") Map<String, Object> condition, @Param("sorter") List<Sorter> sorter);
    public List<T> queryListByWhereSql(@Param("selector") Map<String, Object> selector, @Param("nativeSql") String nativeSql, @Param("sorter") List<Sorter> sorter);


    /**
     * 根据原生sql查询获取返回的值[模型动态化]
     * @return
     */
    public List<Map> queryBySql(@Param("executeSql") String executeSql);

    public Long queryBySqlCount(@Param("executeSqlCount") String executeSql);



    /*
	 * 默认只支持 $MAXLIMIT(默认1000条) 条
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
    List<T> findAll();

    /*
     * 默认只支持 $MAXLIMIT(默认1000条) 条
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
     */
    List<T> findAll(@Param("sorter") List<Sorter> sorter);

    /*
     * TODO 1: 对ids大小做限制,保证性能&防止sql超长
     * TODO 2: 从cache获取所有返回的数据
     * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
     */
    List<T> findAll(Iterable<ID> ids);

    //------------------------------ R END ---------------------------//



    //------------------------------  pagination END ---------------------------//
    /**
     * 增加排序支持
     * @param condition
     * @param offset
     * @param rows
     * @return
     */
    public List<T> queryPage(@Param("selector") Map<String, Object> selector, @Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                             @Param("sorter") List<Sorter> sorter);
    public List<T> queryPageByWhereSql(@Param("selector") Map<String, Object> selector, @Param("nativeSql") String nativeSql, @Param("offset") int offset, @Param("rows") int rows,
                                       @Param("sorter") List<Sorter> sorter);

    /**
     * 根据条件进行数量的查询   和 queryPage 配套使用; 也可独立使用
     *
     * @param condition
     * @return 返回符合条件的泛型参数对应表中的数量
     */
    public int count(@Param("condition") Map<String, Object> condition);
    public int countByWhereSql(@Param("nativeSql") String nativeSql);
    //------------------------------  pagination END ---------------------------//



    //------------------------------ D START ---------------------------//
    /**
     * 根据条件集合删除对象
     *
     * @param condition
     */
    public int deleteByCondition(@Param("condition") Map<String, Object> condition);
    public int deleteByWhereSql(@Param("nativeSql") String nativeSql);
    //------------------------------ D END ---------------------------//


    //------------------------------ spring-data CrudRepository END ---------------------------//
    /**
     * 取得泛型类型
     *
     * @return
     */
    public Class<T> getEntityClass();

}
