package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Customertransfer;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.CustomertracehistoryQuery;
import cn.nine.crm.query.CustomertransferQuery;
import cn.nine.crm.service.*;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * (Customertransfer)表Controller
 *
 * @author 申林
 * @since 2020-05-31 00:40:32
 */
@Controller
@RequestMapping("customertransfer")
@SuppressWarnings(value = "all")/*抑制警告*/
public class CustomertransferController extends BaseController<Customertransfer,Long, CustomertransferQuery>{
    private ICustomertransferService customertransferService;
    private ICustomerService customerService;
    private ITenantService tenantService;
    private IEmployeeService employeeService;
    private IDepartmentService departmentService;

    @Autowired
    public void setCustomertransferService(ICustomertransferService customertransferService) {
        this.customertransferService = customertransferService;
    }

    @Autowired
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Autowired
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/getTransuserEmployee")
    @ApiOperation("获得市场部经理的员工")
    public Result getAllEmployeebyDepartmentName(@RequestBody CustomertransferQuery customertransferQuery) {
        List<Employee> employeeList = departmentService.getManagerByDepartMentName(customertransferQuery.getDepartmentName());
        return Result.ok(employeeList);
    }

    @PostMapping("/getMarketingEmployee")
    @ApiOperation("获得市场部的员工")
    public Result getMarketingEmployee(@RequestBody CustomertransferQuery customertransferQuery) {
        List<Employee> employeeList = employeeService.findEmployeeByDepartmentName(customertransferQuery.getDepartmentName());
        return Result.ok(employeeList);
    }

    @PostMapping("/gettraceCustomerNoResource")
    @ApiOperation("获取不在资源池的顾客")
    public Result getdevCustomer(@RequestBody CustomertransferQuery customertransferQuery) {
        List<Customer> customerList = customerService.getreSourceByState(customertransferQuery.getState());
        return Result.ok(customerList);
    }

    @PostMapping("/getAllTenant")
    @ApiOperation("跟进历史记录页面查询所有租户")
    public Result getAllTenantList() {
        List<Tenant> tenantList = tenantService.findAll();
        return Result.ok(tenantList);
    }
}
