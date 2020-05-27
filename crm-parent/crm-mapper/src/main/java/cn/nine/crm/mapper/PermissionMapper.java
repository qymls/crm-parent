package cn.nine.crm.mapper;

import cn.nine.crm.domain.Permission;
import cn.nine.crm.query.PermissionQuery;

import java.util.List;
import java.util.Set;

/**
 * (Permission)表数据库访问层
 *
 * @author 申林
 * @since 2020-05-26 15:09:36
 */
public interface PermissionMapper extends BaseMapper<Permission,Long,PermissionQuery>{

    Set<String> findPermissionsByID(Long id);


    Set<Permission> findPermissionsByMenu(Long id);

    /**
     * 多对多查询方法
     * @param id
     * @return
     */
    List<Permission> roleFindPermission(Long id);

    List<Permission> findAllPermission();

}
