package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Customerdevplan;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.query.CustomerdevplanQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.ICustomerdevplanService;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * (Customerdevplan)表Controller
 *
 * @author 申林
 * @since 2020-05-30 21:13:33
 */
@Controller
@RequestMapping("customerdevplan")
@SuppressWarnings(value = "all")/*抑制警告*/
public class CustomerdevplanController extends BaseController<Customerdevplan,Long, CustomerdevplanQuery>{
    private ICustomerdevplanService customerdevplanService;
    private ICustomerService customerService;
    private ITenantService tenantService;
    @Autowired
    public void setCustomerdevplanService(ICustomerdevplanService customerdevplanService) {
        this.customerdevplanService = customerdevplanService;
    }

    @Autowired
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    public Result save(@RequestBody Customerdevplan customerdevplan) {
        customerdevplan.setInputuser((Employee) SecurityUtils.getSubject().getPrincipal());
        customerdevplan.setInputtime(new Date());
        return super.save(customerdevplan);
    }

    @PostMapping("/getdevCustomer")
    @ApiOperation("顾客页面修改state,与上方代码重复可优化")
    public Result getdevCustomer(@RequestBody CustomerdevplanQuery customerdevplanQuery) {
        List<Customer> customerList = customerService.getdevCustomer(customerdevplanQuery.getStatus());
        return Result.ok(customerList);
    }

    @PostMapping("/getAllTenant")
    @ApiOperation("潜在客户页面查询所有租户")
    public Result getAllTenantList() {
        List<Tenant> tenantList = tenantService.findAll();
        return Result.ok(tenantList);
    }
}
