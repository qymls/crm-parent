package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.query.MenuQuery;
import cn.nine.crm.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * (Menu)表Controller
 *
 * @author 申林
 * @since 2020-05-24 11:51:45
 */
@Controller
@RequestMapping("menu")
@SuppressWarnings(value = "all")/*抑制警告*/
public class MenuController extends BaseController<Menu,Long, MenuQuery>{
    private IMenuService menuService;

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }
}