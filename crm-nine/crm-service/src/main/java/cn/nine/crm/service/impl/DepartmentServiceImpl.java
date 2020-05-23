package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Department;
import cn.nine.crm.query.DepartmentQuery;
import cn.nine.crm.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl extends BaseServiceImpl<Department,Long, DepartmentQuery> implements DepartmentService {
}
