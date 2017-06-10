package com.power.core.service;



import com.power.core.dao.IBaseDAO;
import com.power.core.domain.BaseDomain;
import com.power.core.domain.BizData4Page;
import com.power.core.domain.Sorter;

import java.util.List;
import java.util.Map;

/**
 * 进行分页数据查询的service
 * <p/>
 * 创建时间: 14-9-3 下午9:57<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public interface IPageService<D extends IBaseDAO,T extends BaseDomain> {
    /**
     * 分页查询  如果 condition 中 有 key 为 "dataPermSql" 说明是 admin后台 数据权限对应的sql过滤条件
     * @param selector 查询多少个字段
     * @param condition 查询条件
     * @param offset
     * @param rows
     * @param sorter
     * @return
     */
    public BizData4Page queryPage(Map<String, Object> selector, Map<String, Object> condition, int curPage, int offset, int rows,
                                  List<Sorter> sorter);

    public BizData4Page queryPageByWhereSql(Map<String, Object> selector, String nativeSql, int curPage, int offset, int rows,
                                            List<Sorter> sorter);
}
