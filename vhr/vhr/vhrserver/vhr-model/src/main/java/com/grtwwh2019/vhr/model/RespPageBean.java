package com.grtwwh2019.vhr.model;

import java.util.List;

/**
 * 分页查询结果bean，保存分页查询的结果
 * 包含 当前展示的数据，以及一共查询了多少条记录
 */
public class RespPageBean {

    private Long total; // 总记录数
    private List<?> data; // 查了多少页

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
