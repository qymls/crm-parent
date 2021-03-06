package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.domain.Permission;
import cn.nine.crm.domain.Role;
import cn.nine.crm.query.PermissionQuery;
import cn.nine.crm.query.RoleQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.service.IPermissionService;
import cn.nine.crm.service.IRoleService;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.util.PageList;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * (Role)表Controller
 *
 * @author 申林
 * @since 2020-05-26 18:02:17
 */
@Controller
@RequestMapping("role")
@SuppressWarnings(value = "all")/*抑制警告*/
public class RoleController extends BaseController<Role, Long, RoleQuery> {
    private IRoleService roleService;
    private IPermissionService permissionService;
    private IMenuService menuService;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("permission/findPageByQuery")
    @ResponseBody
    @ApiOperation("角色新增修该页面的所有权限展示")
    public PageList<Permission> findAll(PermissionQuery permissionQuery) {
        PageList<Permission> pageUtil = permissionService.selectForPage(permissionQuery);
        return pageUtil;
    }

    @PostMapping("/Menu/findAll")
    @ResponseBody
    @ApiOperation("角色配置页面，查询菜单树")
    public List<Menu> findAllMenu() {
        return menuService.findAll();
    }

    @PostMapping("/Menu/newTreeDate")
    @ResponseBody
    @ApiOperation("角色配置页面,回显已有菜单")
    public List<Menu> findAllMenunewTreeDate(Long[] ids) {
        return menuService.findAllMenunewTreeDate(ids);
    }

    @PostMapping("/Menu/findAllRolePermissionMenuByRoleId")
    @ResponseBody
    @ApiOperation("通过角色id查询角色所有权限")
    public List<Menu> findAllRolePermissionMenuByRoleId(Long id) {
        return menuService.findAllRolePermissionMenuByRoleId(id);
    }

    /**
     * 角色直接配置菜单，保存时的方法
     *
     * @param ids
     * @return
     */
    @PostMapping("/Menu/getLastMenuByRoleSave")
    @ResponseBody
    @ApiOperation("给该角色保存配置的权限")
    public HashMap<Object, Object> getLastMenuByRoleSave(Long[] ids, Long roleid) {
        List<Menu> byRoleSave = menuService.getLastMenuByRoleSave(ids);
        Role role = roleService.findOne(roleid);
        role.getPermissionList().clear();/*清空，配置的菜单都只有idnex权限*/
        for (Menu menu : byRoleSave) {
            String[] strings = menu.getUrl().split("/");/*根据菜单的url判断名称拼接*/
            String permisionUrldefault = strings[strings.length - 1];
            Set<Permission> permissionsByMenu = permissionService.findPermissionsByMenu(menu.getId());
            if (permissionsByMenu.size() > 0) {/*判断该菜单是否需要权限，如果没有就去添加一下权限*/
                boolean hasIndexPermiss = false;
                Permission newpermission = null;
                for (Permission byMenu : permissionsByMenu) {
                    if (byMenu.getUrl().equals("/" + permisionUrldefault + "/index")) {/*如果存在index权限那么直接设置即可*/
                        newpermission = byMenu;
                        hasIndexPermiss = true;
                    }
                }
                if (hasIndexPermiss && newpermission != null) {/*permissiom中有这个菜单的index权限*/
                    if (!role.getPermissionList().contains(newpermission)) {/*不重复添加*/
                        role.getPermissionList().add(newpermission);
                        roleService.update(role);
                    }
                } else {/*没有indexx权限的，添加一个*/
                    Permission permissionTemp = new Permission();
                    permissionTemp.setName(menu.getName() + "页面权限");
                    permissionTemp.setUrl("/" + permisionUrldefault + "/index");
                    permissionTemp.setSn(permisionUrldefault + ":" + "index");
                    permissionTemp.setMenu(menu);
                    permissionService.save(permissionTemp);
                    role.getPermissionList().add(permissionTemp);/*给role添加这一个权限*/
                    roleService.update(role);/*保存*/
                }
            } else {/*为空就添加一个页面权限即可*/
                Permission permissionTemp = new Permission();
                permissionTemp.setName(menu.getName() + "页面权限");
                permissionTemp.setUrl("/" + permisionUrldefault + "/index");
                permissionTemp.setSn(permisionUrldefault + ":" + "index");
                permissionTemp.setMenu(menu);
                permissionService.save(permissionTemp);
                role.getPermissionList().add(permissionTemp);/*给role添加这一个权限*/
                roleService.update(role);/*保存*/

            }
        }
        roleService.update(role);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

}
