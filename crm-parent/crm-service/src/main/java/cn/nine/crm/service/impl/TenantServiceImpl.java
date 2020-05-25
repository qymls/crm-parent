package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant,Long,TenantQuery> implements ITenantService {

}
