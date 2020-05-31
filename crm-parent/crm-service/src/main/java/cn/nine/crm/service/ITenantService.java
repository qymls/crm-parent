package cn.nine.crm.service;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.query.TenantQuery;
/**
 * (Tenant)表Service层接口
 *
 * @author ReX
 * @since 2020-05-28 14:55:21
 */
public interface ITenantService extends IBaseService<Tenant,Long,TenantQuery>{

    void settleIn(TenantSettleInDto dto);
}