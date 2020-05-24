package cn.nine.crm.service;

import cn.nine.crm.util.PageList;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T, ID extends Serializable, Q> {
    /**
     * 查询所有
     *
     * @return
     */
    List<T> findAll();

    /**
     * 带参数查询所有
     *
     * @param query
     * @return
     */
    List<T> selectForList(Q query);

    /**
     * 查询总记录数，带条件
     *
     * @param query
     * @return
     */
    Long selectForCount(Q query);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    T findOne(ID id);

    /**
     * 保存
     *
     * @param obj
     */
    void save(T obj);

    /**
     * 修改
     *
     * @param obj
     */
    void update(T obj);

    /**
     * 删除
     *
     * @param id
     */
    void delete(ID id);

    PageList<T> selectForPage(Q query);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(List<Long> ids);
}
