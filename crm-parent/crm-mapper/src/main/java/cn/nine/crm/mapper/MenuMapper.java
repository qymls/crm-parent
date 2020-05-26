package cn.nine.crm.mapper;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.query.MenuQuery;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author 申林
 * @since 2020-05-24 09:02:58
 */
public interface MenuMapper extends BaseMapper<Menu,Long, MenuQuery>{

    Menu findAllByParentId(Long id);

    List<Menu> findByName(String name);

    List<Menu> getStairMenu();

    List<Menu> findMenuByEmployeeId(Long id);

    List<Menu> findAllRolePermissionMenuByRoleId(Long id);
}
