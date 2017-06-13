package com.power.core.domain.wrapper;

import java.util.List;

/**
 * 集合包装类，保证json传输体都为对象而非数组
 * <p/>
 * 创建时间: 14/12/27 下午10:03<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class ListWrapper<T> extends BaseWrapper{
    private List<T> lists;

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}
