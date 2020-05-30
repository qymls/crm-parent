package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Customertracehistory;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.query.CustomerdevplanQuery;
import cn.nine.crm.query.CustomertracehistoryQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.ICustomertracehistoryService;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * (Customertracehistory)表Controller
 *
 * @author 申林
 * @since 2020-05-30 23:01:04
 */
@Controller
@RequestMapping("customertracehistory")
@SuppressWarnings(value = "all")/*抑制警告*/
public class CustomertracehistoryController extends BaseController<Customertracehistory,Long, CustomertracehistoryQuery>{
    private ICustomertracehistoryService customertracehistoryService;
    private ICustomerService customerService;
    private ITenantService tenantService;
    private IEmployeeService employeeService;

    @Autowired
    public void setCustomertracehistoryService(ICustomertracehistoryService customertracehistoryService) {
        this.customertracehistoryService = customertracehistoryService;
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

    @PostMapping("/getMarketingEmployee")
    @ApiOperation("跟进历史页面查询所有员工通过部门名称")
    public Result getAllEmployeebyDepartmentName(@RequestBody CustomertracehistoryQuery customertracehistoryQuery) {
        List<Employee> employeeList = employeeService.findEmployeeByDepartmentName(customertracehistoryQuery.getDepartmentName());
        return Result.ok(employeeList);
    }

    @PostMapping("/gettraceCustomer")
    @ApiOperation("获取可跟进的用户，通过status")
    public Result getdevCustomer(@RequestBody CustomertracehistoryQuery customertracehistoryQuery) {
        List<Customer> customerList = customerService.getdevCustomer(customertracehistoryQuery.getStatus());
        return Result.ok(customerList);
    }

    @PostMapping("/getAllTenant")
    @ApiOperation("跟进历史记录页面查询所有租户")
    public Result getAllTenantList() {
        List<Tenant> tenantList = tenantService.findAll();
        return Result.ok(tenantList);
    }

}
