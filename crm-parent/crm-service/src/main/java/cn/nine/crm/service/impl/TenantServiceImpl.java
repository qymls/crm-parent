package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.mapper.TenantMapper;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Tenant)表Service层接口
 *
 * @author ReX
 * @since 2020-05-28 14:55:22
 */
 @Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant,Long,TenantQuery> implements ITenantService{

    private TenantMapper tenantMapper;

    @Autowired
    public void setTenantMapper(TenantMapper tenantMapper) {
        this.tenantMapper = tenantMapper;
    }
}