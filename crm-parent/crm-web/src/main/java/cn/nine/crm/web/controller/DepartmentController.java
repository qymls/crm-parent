package cn.nine.crm.web.controller;


import cn.nine.crm.domain.Department;
import cn.nine.crm.query.DepartmentQuery;
import cn.nine.crm.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
@SuppressWarnings(value = "all")/*抑制警告*/
public class DepartmentController extends BaseController<Department,Long, DepartmentQuery> {
    private IDepartmentService departmentService;

    @Autowired
    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @ResponseBody
    @RequestMapping(value = "/findTreeData" )
    @ApiOperation("查询子部门")
    public List<Department>  findTreeData(){
          return departmentService.findTreeData();
    }
}
