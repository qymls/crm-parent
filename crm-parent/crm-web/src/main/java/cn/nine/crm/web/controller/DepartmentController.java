package cn.nine.crm.web.controller;


import cn.nine.crm.domain.Department;
import cn.nine.crm.query.DepartmentQuery;
import cn.nine.crm.service.IDepartmentService;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
@SuppressWarnings(value = "all")/*抑制警告*/
public class DepartmentController extends BaseController<Department,Long, DepartmentQuery> {
    private IDepartmentService departmentService;

    @Autowired
    public void setDepartmentService(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("menu/getMenus")
    public Map<Object,Object> getMenus(Long id){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("resrult" , Result.ok());
        map.put("code",20000);
        System.out.println(id);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/findTreeData" )
    public Result findTreeData(){
        try {
            List<Department> treeData = departmentService.findTreeData();
            return Result.ok(treeData);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }
    }
}
