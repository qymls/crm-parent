package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class CustomerController extends BaseController<Customer,Long, CustomerQuery>{
    private ICustomerService customerService;
    private IEmployeeService employeeService;

    @Autowired
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/getAllEmployee")
    @ApiOperation("顾客页面查询所有员工")
    public Result getAllEmployee(){
        List<Employee> employeeList = employeeService.findAll();
        return Result.ok(employeeList);
    }
}
