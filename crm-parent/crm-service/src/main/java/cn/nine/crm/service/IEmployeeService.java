package cn.nine.crm.service;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.EmployeeQuery;

import java.util.List;

//员工接口
public interface IEmployeeService extends IBaseService<Employee,Long,EmployeeQuery>{

    List<Employee> findEmployeeByDepartmentName(String departmentName);
}
