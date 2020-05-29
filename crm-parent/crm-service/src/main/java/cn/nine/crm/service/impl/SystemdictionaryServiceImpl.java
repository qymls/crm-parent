package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Systemdictionary;
import cn.nine.crm.mapper.SystemdictionaryMapper;
import cn.nine.crm.query.SystemdictionaryQuery;
import cn.nine.crm.service.ISystemdictionaryService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Systemdictionary)表Service层接口
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
 @Service
public class SystemdictionaryServiceImpl extends BaseServiceImpl<Systemdictionary,Long,SystemdictionaryQuery> implements ISystemdictionaryService{

    private SystemdictionaryMapper systemdictionaryMapper;

    @Autowired
    public void setSystemdictionaryMapper(SystemdictionaryMapper systemdictionaryMapper) {
        this.systemdictionaryMapper = systemdictionaryMapper;
    }
}