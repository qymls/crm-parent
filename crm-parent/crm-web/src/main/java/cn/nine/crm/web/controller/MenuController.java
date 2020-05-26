package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.query.MenuQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.util.ConstantApi;
import cn.nine.crm.util.Result;
import cn.nine.crm.web.com.baidu.translate.demo.TransApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * (Menu)表Controller
 *
 * @author 申林
 * @since 2020-05-24 11:51:45
 */
@Controller
@RequestMapping("menu")
@SuppressWarnings(value = "all")/*抑制警告*/
public class MenuController extends BaseController<Menu, Long, MenuQuery> {
    private IMenuService menuService;

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 修改方法，并且返回当前修改菜单的所有父菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping("/update_return")
    @ResponseBody
    public List<String> updateReturn(@RequestBody Menu menu, String[] permission) {
        menuService.update(menu);/*修改后返回其父菜单，用于打开父菜单*/
        /*如果直接返回null的话，ajax的success方法不会执行*/
        List<String> allParentName = menuService.findAllParent(menu);/*list中只添加了名字，可以自行添加整个菜单*/
        return allParentName;
    }

    /**
     * 重写save方法，添加创建时间和操作人数据
     *
     * @param menu
     * @return
     */
    @Override
    public Result save(Menu menu) {
        menu.setCreateTime(new Date());
        menuService.save(menu);
        Menu menu1 = menuService.findOne(menu.getId());
        return Result.ok(menu1);
    }

    /**
     * base的批量删除无法瞒住需求，重新写一个方法
     *
     * @param ids
     * @return
     */
    @RequestMapping("/menuBatchDelete")
    @ResponseBody
    public Result menuBatchDelete(Long[] ids) {
        for (Long id : ids) {
            menuService.delete(id);
        }
        return Result.ok();
    }

    /**
     * 查询菜单名称是否重复
     *
     * @param name
     * @return
     */
    @RequestMapping("/findByName")
    @ResponseBody
    public Menu findOne(String name) {
        return menuService.findByName(name);
    }

    /**
     * 百度翻译Api
     *
     * @param name
     * @return
     */
    @RequestMapping("/getEnglishNameByBaiduApi")
    @ResponseBody
    public String getEnglishNameByBaiduApi(String name) {
        TransApi api = new TransApi(ConstantApi.BaiduFanyi_APP_ID, ConstantApi.BaiduFanyi_SECURITY_KEY);
        String transResult = api.getTransResult(name, "auto", "en");
        return transResult;
    }

    /**
     * 查询所有菜单，用于主页左侧显示
     *
     * @return
     */
    @RequestMapping("/findAllMenuTemp")
    @ResponseBody
    public Map<Object,Object> findAllMenuTemp() {
        HashMap<Object, Object> map = new HashMap<>();
        List<Menu> menuList = menuService.findAll();
        map.put("code",20000);
        map.put("resrult",menuList);
        return map;
    }

    /**
     * 根据用户权限查询菜单
     *
     * @return
     */
/*
    @RequestMapping("/findMenuByEmployeeId")
    @ResponseBody
    public List<Menu> findMenuByEmployeeId() {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();*/
    /*获取登录用户*//*

        return menuService.findMenuByEmployeeId(employee.getId());
    }
*/

    /**
     * 查询所有最后一级菜单
     *
     * @return
     */
    @RequestMapping("/findMenuItem")
    @ResponseBody
    public List<Menu> findMenuItem() {
        return menuService.findMenuItem();
    }

    /**
     * 查询菜单的所有权限
     *
     * @return
     */
/*    @RequestMapping("/getMenuPermission")
    @ResponseBody
    public List<String> getMenuPermission(Long id) {
        ArrayList<String> permissonList = new ArrayList<>();
        Set<Permission> permissionsByMenu = permissionService.findPermissionsByMenu(id);
        for (Permission byMenu : permissionsByMenu) {
            String[] split = byMenu.getUrl().split("/");
            String string = split[split.length - 1];
            if (!string.equals("findAllMenu") && !string.equals("findAllByPage")) {*//*特殊处理一下这两个，自己加的都要在这里来去除*//*
                permissonList.add(string);
            }

        }
        return permissonList;
    }*/

}
