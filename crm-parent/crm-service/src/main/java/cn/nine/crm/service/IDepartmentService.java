package cn.nine.crm.service;


import cn.nine.crm.domain.Department;
import cn.nine.crm.query.DepartmentQuery;

import java.util.List;

public interface IDepartmentService extends IBaseService<Department,Long, DepartmentQuery> {
    //查询子部门
    List<Department> findTreeData();


}
