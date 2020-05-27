package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Permission;
import cn.nine.crm.domain.Role;
import cn.nine.crm.mapper.RoleMapper;
import cn.nine.crm.query.RoleQuery;
import cn.nine.crm.service.IRoleService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 重写role的insert方法，多对多添加
     * @param role
     */
    @Override
    @Transactional
    public void save(Role role) {
        super.save(role);
        List<Permission> permissionList = role.getPermissionList();
        permissionList.forEach(permission -> roleMapper.savePermissions(role.getId(),permission.getId()));
    }

    @Override
    @Transactional
    public void update(Role role) {
        super.update(role);
        roleMapper.deletePermissionsByRoleId(role.getId());
        List<Permission> permissionList = role.getPermissionList();
        permissionList.forEach(permission -> roleMapper.savePermissions(role.getId(),permission.getId()));
    }

    @Override
    @Transactional
    public int batchDelete(List<Long> ids) {
        for (Long id : ids) {/*先删除role_permission的数据*/
            roleMapper.deletePermissionsByRoleId(id);
        }
        return super.batchDelete(ids);
    }
}
