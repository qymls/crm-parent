package cn.nine.crm.utils;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> {
    //返回总条数
    private Long total = 0L;
    //返回分页数据
    private List<T> result = new ArrayList<>();

    public PageList(){

    }
    public PageList(Long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}