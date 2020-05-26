package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Role;
import cn.nine.crm.mapper.RoleMapper;
import cn.nine.crm.query.RoleQuery;
import cn.nine.crm.service.IRoleService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Role)表Service层接口
 *
 * @author 申林
 * @since 2020-05-26 18:02:17
 */
 @Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long,RoleQuery> implements IRoleService{

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
}