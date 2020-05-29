package cn.crm.test;


import cn.nine.crm.mapper.EmployeeMapper;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.ITenantService;
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
    private ITenantService tenant;
    @Autowired
    private IEmployeeService service;

}
