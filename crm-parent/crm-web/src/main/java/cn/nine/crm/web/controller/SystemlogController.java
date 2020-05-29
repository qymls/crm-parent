package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Systemlog;
import cn.nine.crm.query.SystemlogQuery;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.ISystemlogService;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * (Systemlog)表Controller
 *
 * @author 申林
 * @since 2020-05-28 20:20:50
 */
@Controller
@RequestMapping("systemlog")
@SuppressWarnings(value = "all")/*抑制警告*/
public class SystemlogController extends BaseController<Systemlog,Long, SystemlogQuery>{
    private ISystemlogService systemlogService;
    private IEmployeeService employeeService;

    @Autowired
    public void setSystemlogService(ISystemlogService systemlogService) {
        this.systemlogService = systemlogService;
    }

    @Autowired
    @ApiOperation("获得所有员工")
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/getAllEmployee")
    public Result getAllEmployee(){
        List<Employee> employeeList = employeeService.findAll();
        return Result.ok(employeeList);
    }
}
