package cn.nine.crm.mapper;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Role;
import cn.nine.crm.query.EmployeeQuery;

import java.util.List;

//员工类映射器
public interface EmployeeMapper extends BaseMapper<Employee,Long,EmployeeQuery>{

    Employee findByUsername(String username);

    //根据部门查询员工
    Employee findEmployeeByDepartmentName(String departmentName);

    List<Role> findRoleList(Long id);
}
