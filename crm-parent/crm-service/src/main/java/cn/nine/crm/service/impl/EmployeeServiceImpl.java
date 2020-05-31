package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Role;
import cn.nine.crm.mapper.EmployeeMapper;
import cn.nine.crm.query.EmployeeQuery;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;


@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee,Long,EmployeeQuery>
        implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    //根据部门id查员工
    @Override
    public List<Employee> findEmployeeByDepartmentName(String departmentName) {

        return employeeMapper.findEmployeeByDepartmentName(departmentName);
    }

    /**
     * 保存员工角色表
     * @param
     */
    @Override
    @Transactional
    public void save(Employee employee) {
        super.save(employee);
        //获取所有权限
        List<Role> roles = employee.getRole();
        roles.forEach(role -> employeeMapper.saveRole(role.getId(),employee.getId()));
    }

    @Override
    public void update(Employee employee) {
        super.update(employee);
        //根据id删除拥有角色
        employeeMapper.deleteRole(employee.getId());
        List<Role> roles = employee.getRole();
        roles.forEach(role -> employeeMapper.saveRole(role.getId(),employee.getId()));
    }
}
