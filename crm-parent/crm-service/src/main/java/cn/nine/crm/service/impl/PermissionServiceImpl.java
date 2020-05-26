package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Permission;
import cn.nine.crm.mapper.PermissionMapper;
import cn.nine.crm.query.PermissionQuery;
import cn.nine.crm.service.IPermissionService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * (Permission)表Service层接口
 *
 * @author 申林
 * @since 2020-05-26 15:09:36
 */
 @Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Long,PermissionQuery> implements IPermissionService{

    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
    @Override
    public Set<String> findPermissionsByID(Long id) {
        return permissionMapper.findPermissionsByID(id);
    }

    @Override
    public Set<Permission> findPermissionsByMenu(Long id) {
        return permissionMapper.findPermissionsByMenu(id);
    }
}
