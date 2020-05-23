package cn.nine.crm.service;

import cn.nine.crm.utils.PageList;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T,ID extends Serializable,Q> {
    /**
     * 添加数据
     * @param t
     */
    int insert(T t);

    /**
     * 修改数据
     * @param t
     */
    int update(T t);

    /**
     * 删除数据
     * @param id
     */
    int delete(ID id);

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 查询所有数据
     * @return
     */
    List<T> selectAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    T selectById(ID id);

    /**
     * 分页查询数据
     * @param query
     * @return
     */
    List<T>  selectForList(Q query);

    /**
     * 分页查询
     * @param q
     * @return
     */
    PageList<T> selectForPage(Q q);

    /**
     * 分页查询总条数
     * @param query
     * @return
     */
    Long selectForCount(Q query);
}
