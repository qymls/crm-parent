package cn.nine.crm.service;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.query.TenantQuery;

public interface ITenantService extends IBaseService<Tenant,Long, TenantQuery> {
    void settleIn(TenantSettleInDto dto);
}
