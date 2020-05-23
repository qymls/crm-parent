package cn.nine.crm.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T,ID extends Serializable,Q> {
    /**
     * 添加数据
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int delete(ID id);

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 修改数据
     * @param t
     * @return
     */
    int update(T t);

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
     * @return
     */
    List<T> selectByQuery(Q query);

    /**
     * 分页查询总数
     * @return
     */
    Long selectByCount(Q query);

}
