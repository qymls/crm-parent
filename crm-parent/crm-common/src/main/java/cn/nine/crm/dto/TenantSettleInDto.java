package cn.nine.crm.dto;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Tenant;
import lombok.Data;

@Data
public class TenantSettleInDto {

    private Tenant tenant;

    private Employee employee;
}
