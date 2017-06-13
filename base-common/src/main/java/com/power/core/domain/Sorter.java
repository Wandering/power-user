package com.power.core.domain;

/**
 * 排序器    field desc/asc
 * <p>
 * 创建时间: 16/9/23 上午10:10<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class Sorter {
    private String field;
    private String order;

    public Sorter(String field, SqlOrderEnum sqlOrderEnum) {

        this.field = field;
        this.order = sqlOrderEnum.getAction();
    }

    public String getField() {
        return field;
    }

    public String getOrder() {
        return order;
    }
}
