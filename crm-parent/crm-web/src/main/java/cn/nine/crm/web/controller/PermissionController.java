package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.domain.Permission;
import cn.nine.crm.query.PermissionQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * (Permission)表Controller
 *
 * @author 申林
 * @since 2020-05-26 15:09:36
 */
@Controller
@RequestMapping("permission")
@SuppressWarnings(value = "all")/*抑制警告*/
public class PermissionController extends BaseController<Permission,Long, PermissionQuery>{
    private IPermissionService permissionService;
    private IMenuService menuService;

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/findMenuItem")
    @ResponseBody
    public List<Menu> findMenuItem() {
        return menuService.findMenuItem();
    }
}
