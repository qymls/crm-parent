package cn.nine.crm.service.impl;

import cn.nine.crm.service.BaseService;
import cn.nine.crm.mapper.BaseMapper;
import cn.nine.crm.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public class BaseServiceImpl<T,ID extends Serializable,Q> implements BaseService<T,ID,Q> {

    @Autowired
    private BaseMapper<T,ID,Q> baseMapper;

    /**
     * 添加数据
     *
     * @param t
     */
    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    /**
     * 修改数据
     *
     * @param t
     */
    @Override
    public int update(T t) {
        return baseMapper.update(t);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override
    public int delete(ID id) {
        return baseMapper.delete(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void batchDelete(List<Long> ids) {
        baseMapper.batchDelete(ids);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    @Override
    public T selectById(ID id) {
        return baseMapper.selectById(id);
    }

    /**
     * 列表查询数据
     *
     * @param q
     * @return
     */
    @Override
    public List<T> selectForList(Q q) {
        return baseMapper.selectByQuery(q);
    }

    /**
     * 分页查询
     *
     * @param q
     * @return
     */
    @Override
    public PageList<T> selectForPage(Q q) {
        //查询总条数
        Long total = baseMapper.selectByCount(q);
        if(total == 0){
            return new PageList<>();
        }
        //查询列表
        List<T> list = baseMapper.selectByQuery(q);
        return new PageList<T>(total,list);
    }

    /**
     * 分页查询总条数
     *
     * @param q
     * @return
     */
    @Override
    public Long selectForCount(Q q) {
        return baseMapper.selectByCount(q);
    }
}
