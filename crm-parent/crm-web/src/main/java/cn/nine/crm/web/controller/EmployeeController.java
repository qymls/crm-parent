package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.EmployeeQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Employee,Long,EmployeeQuery> {
}
