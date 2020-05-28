package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Menu;
import cn.nine.crm.domain.Permission;
import cn.nine.crm.dto.MenuPermissionDto;
import cn.nine.crm.query.MenuQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.service.IPermissionService;
import cn.nine.crm.util.ConstantApi;
import cn.nine.crm.util.Result;
import cn.nine.crm.web.com.baidu.translate.demo.TransApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private IPermissionService permissionService;

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 修改方法，并且返回当前修改菜单的所有父菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping("/update_return")
    @ResponseBody
    public List<String> updateReturn(@RequestBody MenuPermissionDto menuPermissionDto) {
        Menu menu = menuPermissionDto.getMenu();
        String[] permission = menuPermissionDto.getPermission();
        menuService.update(menu);/*修改后返回其父菜单，用于打开父菜单*/
        /*如果直接返回null的话，ajax的success方法不会执行*/
        List<String> allParentName = menuService.findAllParent(menu);/*list中只添加了名字，可以自行添加整个菜单*/
        if (permission != null && permission.length > 0 && StringUtils.isNoneBlank(menu.getUrl())) {/*只适用于新添加的菜单,而已，方便添加权限而已*/
            System.out.println("开始添加或修改权限了");
            Set<Permission> permissionsByMenu = permissionService.findPermissionsByMenu(menu.getId());
            ArrayList<Permission> noaddArray = new ArrayList<>();
            if (permissionsByMenu.size() > 0) {
                for (Permission permissions : permissionsByMenu) {
                    try {
                        permissionService.delete(permissions.getId());/*先删除在添加*/
                    } catch (Exception e) {
                        e.printStackTrace();
                        noaddArray.add(permissions);/*将不能删除的提取出来就不添加了*/
                    }

                }
            }
            String[] strings = menu.getUrl().split("/");
            String permisionUrldefault = strings[strings.length - 1];
            loop:
            for (String url : permission) {
                String sn = "";
                String name = "";
                switch (url) {
                    case "index":
                        url = "/" + permisionUrldefault + "/index";
                        sn = permisionUrldefault + ":" + "index";
                        name = menu.getName() + "页面权限";
                        break;
                    case "selectForPage":
                        url = "/" + permisionUrldefault + "/selectForPage";
                        sn = permisionUrldefault + ":" + "selectForPage";
                        name = menu.getName() + "列表权限";
                        break;
                    case "save":
                        url = "/" + permisionUrldefault + "/save";
                        sn = permisionUrldefault + ":" + "save";
                        name = menu.getName() + "保存权限";
                        break;
                    case "update":
                        url = "/" + permisionUrldefault + "/update";
                        sn = permisionUrldefault + ":" + "update";
                        name = menu.getName() + "修改权限";
                        break;
                    case "delete":
                        url = "/" + permisionUrldefault + "/delete/*";
                        sn = permisionUrldefault + ":" + "delete";
                        name = menu.getName() + "删除权限";
                        break;
                    case "batchDelete":
                        url = "/" + permisionUrldefault + "/batchDelete";
                        sn = permisionUrldefault + ":" + "batchDelete";
                        name = menu.getName() + "批量删除";
                        break;
                }
                Permission permissionTemp = new Permission();
                permissionTemp.setUrl(url);
                permissionTemp.setSn(sn);
                permissionTemp.setMenu(menu);
                permissionTemp.setName(name);
                if (noaddArray.size() > 0) {
                    for (Permission s : noaddArray) {
                        if (permissionTemp.getUrl().equals(s.getUrl())) {/*如果和其中的一个相等了就是不添加跳出*/
                            continue loop;
                        }
                    }
                    permissionService.save(permissionTemp);/*保存了*/
                } else {
                    permissionService.save(permissionTemp);/*保存了*/
                }
            }
        }
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
            try {
                menuService.delete(id);
            } catch (Exception e) {
                return Result.error(e.getMessage());
            }

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
    public Map<Object, Object> findAllMenuTemp() {
        HashMap<Object, Object> map = new HashMap<>();
        List<Menu> menuList = menuService.findAll();
        map.put("code", 20000);
        map.put("resrult", menuList);
        return map;
    }

    /**
     * 根据用户权限查询菜单
     *
     * @return
     */
    @RequestMapping("/findMenuByEmployeeId")
    @ResponseBody
    public  Map<Object, Object> findMenuByEmployeeId() {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        /*获取登录用户*/
        List<Menu> menuByEmployeeId = menuService.findMenuByEmployeeId(employee.getId());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code", 20000);
        map.put("resrult", menuByEmployeeId);
        return map;
    }

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
    @RequestMapping("/getMenuPermission")
    @ResponseBody
    public List<String> getMenuPermission(Long id) {
        ArrayList<String> permissonList = new ArrayList<>();
        Set<Permission> permissionsByMenu = permissionService.findPermissionsByMenu(id);
        for (Permission byMenu : permissionsByMenu) {
            String[] split = byMenu.getUrl().split("/");
            String string;
            if ("delete".equals(split[split.length - 2])) {
                string = split[split.length - 2];
            } else {
                string = split[split.length - 1];
            }
            if (!string.equals("update_return") && !string.equals("menuBatchDelete")) {/*特殊处理一下这两个，自己加的都要在这里来去除*/
                permissonList.add(string);
            }

        }
        return permissonList;
    }

}
