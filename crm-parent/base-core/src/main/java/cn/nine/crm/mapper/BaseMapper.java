package cn.nine.crm.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T, ID extends Serializable, Q> {
    List<T> findAll();

    List<T> selectForPage(Q query);

    List<T> selectForList(Q query);

    Long selectForCount(Q query);

    T findOne(ID id);

    int save(T obj);

    int update(T obj);

    int delete(ID id);

    int batchDelete(List<Long> ids);

}
