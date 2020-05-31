package cn.crm.test;


import cn.nine.crm.domain.Department;
import cn.nine.crm.mapper.DepartmentMapper;
import cn.nine.crm.mapper.EmployeeMapper;
import cn.nine.crm.query.DepartmentQuery;
import cn.nine.crm.service.IDepartmentService;
import cn.nine.crm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-service.xml")
public class TestCrud {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private IDepartmentService service;
     @Test
     public void test() throws Exception{
     DepartmentQuery departmentQuery = new DepartmentQuery();
     departmentQuery.setCurrentPage(1);
     PageList<Department> departmentPageList = service.selectForPage(departmentQuery);
     System.out.println(departmentPageList.getList());
    }
}
