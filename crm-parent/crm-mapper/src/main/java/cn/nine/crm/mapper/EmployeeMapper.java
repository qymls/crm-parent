package cn.nine.crm.mapper;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Role;
import cn.nine.crm.query.EmployeeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


//员工类映射器
public interface EmployeeMapper extends BaseMapper<Employee,Long,EmployeeQuery>{

    Employee findByUsername(String username);

    List<Role> findRoleList(Long id);

    //保存角色表
    void saveRole(@Param("role_id")Long role_id,@Param("employee_id")Long employee_id);

    //删除所属角色
    void deleteRole(Long employee_id);

    List<Employee> findEmployeeByDepartmentName(String departmentName);
}
