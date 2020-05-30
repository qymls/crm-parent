package cn.nine.crm.mapper;


import cn.nine.crm.domain.Customer;
import cn.nine.crm.domain.Department;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.DepartmentQuery;

import java.util.List;

/**
 * 映射器接口，调用xml的sql语句的api接口
 */
public interface DepartmentMapper extends BaseMapper<Department,Long, DepartmentQuery>{
    //查询子部门
    List<Department>findTreeData();

    List<Employee> getManagerByDepartMentName(String name);
}
