package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Department;
import cn.nine.crm.query.DepartmentQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/dept")
@CrossOrigin//解决跨域请求
public class DepartmentController extends BaseController<Department,Long, DepartmentQuery>{

}
