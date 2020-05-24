package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TenantServiceImpl extends BaseServiceImpl<Tenant, Long, TenantQuery> implements TenantService {
}
