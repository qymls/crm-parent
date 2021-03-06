package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Systemdictionaryitem;
import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.ISystemdictionaryitemService;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * (Customer)表Controller
 *
 * @author 申林
 * @since 2020-05-29 22:15:36
 */
@Controller
@RequestMapping("customer")
@SuppressWarnings(value = "all")/*抑制警告*/
public class CustomerController extends BaseController<Customer, Long, CustomerQuery> {
    private ICustomerService customerService;
    private IEmployeeService employeeService;
    private ITenantService tenantService;
    private ISystemdictionaryitemService systemdictionaryitemService;

    @Autowired
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Autowired
    public void setSystemdictionaryitemService(ISystemdictionaryitemService systemdictionaryitemService) {
        this.systemdictionaryitemService = systemdictionaryitemService;
    }

    @Override
    public Result save(@RequestBody Customer customer) {
        customer.setInputuser((Employee) SecurityUtils.getSubject().getPrincipal());
        customer.setInputtime(new Date());
        customer.setState(true);/*默认正常客户*/
        return super.save(customer);
    }

    @PostMapping("/getAllEmployeebyDepartmentName")
    @ApiOperation("顾客页面查询所有员工通过部门名称")
    public Result getAllEmployeebyDepartmentName(@RequestBody CustomerQuery customerQuery) {
        List<Employee> employeeList = employeeService.findEmployeeByDepartmentName(customerQuery.getDepartmentName());
        return Result.ok(employeeList);
    }

    @PostMapping("/getAllTenant")
    @ApiOperation("顾客页面查询所有租户")
    public Result getAllTenantList() {
        List<Tenant> tenantList = tenantService.findAll();
        return Result.ok(tenantList);
    }

    @PostMapping("/getAllJob")
    @ApiOperation("顾客页面查询所有职位")
    public Result getAllJob(@RequestBody CustomerQuery customerQuery) {
        List<Systemdictionaryitem> systemdictionaryitemList = systemdictionaryitemService.selectByName(customerQuery.getJonName());
        return Result.ok(systemdictionaryitemList);
    }

    @PostMapping("/moveToResource")
    @ApiOperation("顾客页面修改state")
    public Result moveToResource(@RequestBody CustomerQuery customerQuery) {
        for (Long id : customerQuery.getIds()) {
            customerService.updateStateByid(false,id);
        }
        return Result.ok();
    }

    @PostMapping("/movetoCustomter")
    @ApiOperation("顾客页面修改state,与上方代码重复可优化")
    public Result movetoCustomter(@RequestBody CustomerQuery customerQuery) {
        for (Long id : customerQuery.getIds()) {
            customerService.updateStateByid(true,id);
        }
        return Result.ok();
    }

}
