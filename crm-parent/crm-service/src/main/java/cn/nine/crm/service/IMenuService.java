package cn.nine.crm.service;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.query.MenuQuery;

import java.util.List;

/**
 * (Menu)表Service层接口
 *
 * @author 申林
 * @since 2020-05-24 11:21:05
 */
public interface IMenuService extends IBaseService<Menu,Long,MenuQuery>{

    List<String> findAllParent(Menu menu);

    Menu findByName(String name);

    List<Menu> findMenuByEmployeeId(Long id);

    List<Menu> findMenuItem();

    List<Menu> findAllMenunewTreeDate(Long[] ids);/*菜单权限配置是需要的显示菜单*/

    List<Menu> findAllRolePermissionMenuByRoleId(Long id);/*通过rlole查询所属权限，因为要单独处理所以需要单独写*/

    List<Menu> getLastMenuByRoleSave(Long[] ids);

}
