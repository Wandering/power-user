package com.power.core.utils;


import com.google.common.collect.Lists;
import com.power.core.domain.Sorter;
import com.power.core.domain.SqlOrderEnum;

import java.util.List;

/**
 * TODO 一句话描述该类用途
 * <p>
 * 创建时间: 16/9/24 下午9:46<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class SorterBuilder {
    public static final List<Sorter> sorterList(String field, SqlOrderEnum sqlOrderEnum){
        List<Sorter> sorterList = Lists.newArrayList();
        Sorter sorter = new Sorter(field, sqlOrderEnum);
        sorterList.add(sorter);
        return sorterList;
    }
}
