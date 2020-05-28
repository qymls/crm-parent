package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.mapper.TenantMapper;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant,Long,TenantQuery> implements ITenantService {


    @Autowired
    private TenantMapper tenantMapper;


    @Override
    public void settleIn(TenantSettleInDto dto) {

    }


}
