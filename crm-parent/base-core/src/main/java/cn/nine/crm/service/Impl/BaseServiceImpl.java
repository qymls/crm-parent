package cn.nine.crm.service.Impl;

import cn.nine.crm.mapper.BaseMapper;
import cn.nine.crm.query.BaseQuery;
import cn.nine.crm.service.IBaseService;
import cn.nine.crm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public class BaseServiceImpl<T, ID extends Serializable, Q> implements IBaseService<T, ID, Q> {
    @Autowired
    private BaseMapper<T, ID, Q> baseMapper;

    @Override
    public List<T> findAll() {
        return baseMapper.findAll();
    }

    @Override
    public List<T> selectForList(Q query) {
        return baseMapper.selectForList(query);
    }

    @Override
    public Long selectForCount(Q query) {
        return baseMapper.selectForCount(query);
    }

    @Override
    public T findOne(ID id) {
        return baseMapper.findOne(id);
    }

    @Override
    @Transactional
    public void save(T obj) {
        baseMapper.save(obj);
    }

    @Override
    @Transactional
    public void update(T obj) {
        baseMapper.update(obj);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        baseMapper.delete(id);
    }

    @Override
    public PageList<T> selectForPage(Q query) {
        BaseQuery baseQuery = (BaseQuery) query;
        Long count = baseMapper.selectForCount((Q) baseQuery);
        if (count == 0){
            return new PageList<>();
        }
        PageList<T> pageList = new PageList<>(baseQuery.getCurrentPage(), baseQuery.getPageSize(), Integer.parseInt(String.valueOf(count)));
        baseQuery.setCurrentPage(pageList.getCurrentPage());
        baseQuery.setPageSize(pageList.getPageSize());
        List<T> list = baseMapper.selectForPage((Q) baseQuery);
        pageList.setList(list);
        return pageList;
    }

    @Override
    @Transactional
    public int batchDelete(List<Long> ids) {
        return baseMapper.batchDelete(ids);
    }
}
