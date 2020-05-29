package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Systemdictionaryitem;
import cn.nine.crm.mapper.SystemdictionaryitemMapper;
import cn.nine.crm.query.SystemdictionaryitemQuery;
import cn.nine.crm.service.ISystemdictionaryitemService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Systemdictionaryitem)表Service层接口
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
 @Service
public class SystemdictionaryitemServiceImpl extends BaseServiceImpl<Systemdictionaryitem,Long,SystemdictionaryitemQuery> implements ISystemdictionaryitemService{

    private SystemdictionaryitemMapper systemdictionaryitemMapper;

    @Autowired
    public void setSystemdictionaryitemMapper(SystemdictionaryitemMapper systemdictionaryitemMapper) {
        this.systemdictionaryitemMapper = systemdictionaryitemMapper;
    }
}