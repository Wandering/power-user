package com.power.core.utils;

import com.google.common.base.Strings;
import com.power.core.dao.IBaseDAO;
import com.power.core.domain.BizData4Page;
import com.power.core.domain.Sorter;
import com.power.core.domain.SqlOrderEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BizData4Page 构建者
 * <p/>
 * 创建时间: 15/7/31 下午2:57<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class BizData4PageBuilder {

    /**
     * 生成 BizData4Page 实例
     *
     * @param dao
     * @param conditions
     * @param curPage
     * @param offset
     * @param rows
     * @return
     */
    public static BizData4Page createBizData4Page(IBaseDAO dao, Map<String, Object> selector, Map<String, Object> conditions, String nativeSql, int curPage, int offset, int rows, List<Sorter> sorter) {
        String orderBy = null;
        String sortBy = null;
        if (conditions.containsKey("orderBy")) {
            orderBy = conditions.get("orderBy").toString();
        }
        if (conditions.containsKey("sortBy")) {
            sortBy = conditions.get("sortBy").toString();
        }

        List<Sorter> sorterList = null;
        if(!Strings.isNullOrEmpty(orderBy) && !Strings.isNullOrEmpty(sortBy)) {
            sorterList = new ArrayList<>();
            sorterList.add(new Sorter(orderBy, SqlOrderEnum.valueOf(sortBy)));
        }

        List mainData = null;
        if(nativeSql == null) {
            mainData = dao.queryPage(selector, conditions, offset, rows, sorterList);
        } else {
            mainData = dao.queryPageByWhereSql(selector, nativeSql, offset, rows, sorterList);
        }
        int records = dao.count(conditions);

//        BizData4Page bizData4Page = new BizData4Page();
//        bizData4Page.setRows(mainData);
//        bizData4Page.setPage(curPage);
//        bizData4Page.setRecords(records);
//
//        int total = records / rows;
//        int mod = records % rows;
//        if (mod > 0) {
//            total = total + 1;
//        }
//        bizData4Page.setTotal(total);
//
//        return bizData4Page;

        return innerCreateBizData4Page(mainData, records, curPage, rows);
    }

    /**
     * 根据给定数据 转换为 BizData4Page    【主要应用在使用分页表格控件 但又不分页的情况】
     * @param datas 数据
     * @param records 数据条数
     * @param curPage 当前页
     * @param rows 当前页要求显示条数
     * @return
     */
    public static final BizData4Page innerCreateBizData4Page(List datas, int records, int curPage, int rows) {
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(datas);
        bizData4Page.setPage(curPage);
        bizData4Page.setRecords(records);

        int total = records / rows;
        int mod = records % rows;
        if (mod > 0) {
            total = total + 1;
        }
        bizData4Page.setTotal(total);

        return bizData4Page;
    }

}
