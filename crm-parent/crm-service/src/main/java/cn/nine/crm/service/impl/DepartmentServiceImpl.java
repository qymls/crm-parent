package cn.nine.crm.service.impl;


import cn.nine.crm.query.DepartmentQuery;
import cn.nine.crm.service.IDepartmentService;
import cn.nine.crm.domain.Department;
import cn.nine.crm.mapper.DepartmentMapper;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department,Long, DepartmentQuery> implements IDepartmentService {

    private DepartmentMapper departmentMapper;

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<Department> findTreeData() {
        List<Department> treeData = departmentMapper.findTreeData();

        return treeData;
    }
}
