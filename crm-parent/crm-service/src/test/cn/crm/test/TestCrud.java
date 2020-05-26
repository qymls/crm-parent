package cn.crm.test;


import cn.nine.crm.domain.Department;
import cn.nine.crm.domain.Employee;
import cn.nine.crm.mapper.EmployeeMapper;
import cn.nine.crm.query.EmployeeQuery;
import cn.nine.crm.service.IDepartmentService;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-service.xml")
public class TestCrud {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEmployeeService service;
    @Test
     public void test() throws Exception{
        EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.setKeyword("admin");
        PageList<Employee> employeePageList = service.selectForPage(employeeQuery);
        System.out.println(employeePageList);
    }

    @Test
     public void testFindAll() throws Exception{
        List<Department> serviceAll = departmentService.findAll();
        serviceAll.forEach(department -> System.out.println(department));
    }

    @Test
     public void testSave() throws Exception{
        Employee employee = new Employee();
        employee.setUsername("bbbb");
        employee.setPassword("12345");
        employee.setTel("11458");
        employee.setEmail("45456@qq.com");
        Department department = new Department();
        department.setId(2L);
        employee.setDepartment(department);
        employee.setInputTime(new Date());
        service.save(employee);
     }
}
