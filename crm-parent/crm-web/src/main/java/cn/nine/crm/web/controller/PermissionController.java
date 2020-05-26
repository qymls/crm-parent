package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Permission;
import cn.nine.crm.query.PermissionQuery;
import cn.nine.crm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }
}