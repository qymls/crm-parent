package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Systemlog;
import cn.nine.crm.mapper.SystemlogMapper;
import cn.nine.crm.query.SystemlogQuery;
import cn.nine.crm.service.ISystemlogService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Systemlog)表Service层接口
 *
 * @author 申林
 * @since 2020-05-28 20:20:50
 */
 @Service
public class SystemlogServiceImpl extends BaseServiceImpl<Systemlog,Long,SystemlogQuery> implements ISystemlogService{

    private SystemlogMapper systemlogMapper;

    @Autowired
    public void setSystemlogMapper(SystemlogMapper systemlogMapper) {
        this.systemlogMapper = systemlogMapper;
    }
}