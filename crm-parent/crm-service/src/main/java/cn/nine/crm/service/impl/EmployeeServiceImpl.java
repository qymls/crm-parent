package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.EmployeeQuery;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee,Long,EmployeeQuery>
        implements IEmployeeService {
}