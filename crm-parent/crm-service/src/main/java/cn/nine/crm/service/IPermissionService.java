package cn.nine.crm.service;

import cn.nine.crm.domain.Permission;
import cn.nine.crm.query.PermissionQuery;

import java.util.Set;

/**
 * (Permission)表Service层接口
 *
 * @author 申林
 * @since 2020-05-26 15:09:36
 */
public interface IPermissionService extends IBaseService<Permission,Long,PermissionQuery>{
    Set<String> findPermissionsByID(Long id);

    Set<Permission> findPermissionsByMenu(Long id);

}
