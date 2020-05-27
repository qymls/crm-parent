package cn.nine.crm.mapper;

import cn.nine.crm.domain.Role;
import cn.nine.crm.query.RoleQuery;
import org.apache.ibatis.annotations.Param;

/**
 * (Role)表数据库访问层
 *
 * @author 申林
 * @since 2020-05-26 18:02:17
 */
public interface RoleMapper extends BaseMapper<Role,Long,RoleQuery>{
    /*保存权限*/
    void savePermissions (@Param("role_id") Long role_id, @Param("permission_id") Long permission_id);

    /*删除权限*/

    void deletePermissionsByRoleId (@Param("role_id") Long role_id);

}
