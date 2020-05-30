package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Tenant;
import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.mapper.EmployeeMapper;
import cn.nine.crm.mapper.TenantMapper;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * (Tenant)表Service层接口
 *
 * @author ReX
 * @since 2020-05-28 14:55:22
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant, Long, TenantQuery> implements ITenantService {

    private EmployeeMapper employeeMapper;
    private TenantMapper tenantMapper;

    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Autowired
    public void setTenantMapper(TenantMapper tenantMapper) {
        this.tenantMapper = tenantMapper;
    }

    @Override
    public void settleIn(TenantSettleInDto dto) {
        //保存机构
        Tenant tenant = dto.getTenant();
        Employee emp = dto.getEmployee();

        Date date = new Date();
        tenant.setPay(true);
        tenant.setRegisterTime(date);
        tenant.setState("0");
        super.save(tenant);

        //保存管理员
        emp.setTenant(tenant);
        emp.setInputTime(date);
        //emp.setSalt();
        //emp.setPassword();

        employeeMapper.save(emp);
    }
}