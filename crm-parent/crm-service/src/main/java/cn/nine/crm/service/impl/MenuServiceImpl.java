package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.mapper.MenuMapper;
import cn.nine.crm.query.BaseQuery;
import cn.nine.crm.query.MenuQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import cn.nine.crm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * (Menu)表Service层接口
 *
 * @author 申林
 * @since 2020-05-24 11:30:22
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long, MenuQuery> implements IMenuService {

    private MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**
     * 初始化菜单管理
     *
     * @return
     */
    @Override
    public List<Menu> findAll() {/*重写FindAll方法，应为需要特殊处理下才能返回的数据*/
        List<Menu> menuList = menuMapper.findAll();
        List<Menu> stairMenuList = menuMapper.getStairMenu();
        for (Menu stairMenu : stairMenuList) {
            stairMenu.setChildren(getMenuTree(menuList, stairMenu.getId()));/*一级菜单*/
        }
        return stairMenuList;
    }

    /**
     * 给一级菜单添加子菜单，递归，
     *
     * @param menuList
     * @param id
     * @return
     */
    private List<Menu> getMenuTree(List<Menu> menuList, Long id) {
        List<Menu> treeList = new ArrayList<>();
        for (Menu menu : menuList) {/*将所有菜单的parentid和传递的菜单id对比，相等就递归调用，并且加到treeList中，用于setChildren*/
            if (menu.getParent() != null) {/*排除一级菜单在进来对比的情况*/
                if (id.equals(menu.getParent().getId())) {/*可能出现空指的放在后面*/
                    menu.setChildren(getMenuTree(menuList, menu.getId()));
                    treeList.add(menu);
                }
            }
        }
        return treeList;
    }

    /**
     * 菜单管理界面
     *
     * @param menuQuery
     * @return
     */

    @Override
    public PageList<Menu> selectForPage(MenuQuery menuQuery) {
        List<Menu> menuList = menuMapper.findAll();/*获得所有的菜单*/
        Long count = menuMapper.selectForCount(menuQuery);
        if (count == 0) {
            return new PageList<>();
        }
        PageList<Menu> pageList = new PageList<>(menuQuery.getCurrentPage(), menuQuery.getPageSize(), Integer.parseInt(String.valueOf(count)));
        menuQuery.setCurrentPage(pageList.getCurrentPage());
        menuQuery.setPageSize(pageList.getPageSize());/*上方纠错分页*/
        List<Menu> stairMenuList = menuMapper.selectForPage(menuQuery);/*获得分页查询的一级菜单*/
        for (Menu stairMenu : stairMenuList) {
            stairMenu.setChildren(getMenuTreeMenu(menuList, stairMenu.getId()));/*一级菜单*/
        }
        pageList.setList(stairMenuList);
        return pageList;
    }


    /**
     * 菜单管理，不用初始化菜单，不需要特殊处理url
     *
     * @param menuList
     * @param id
     * @return
     */
    private List<Menu> getMenuTreeMenu(List<Menu> menuList, Long id) {
        List<Menu> treeList = new ArrayList<>();
        for (Menu menu : menuList) {/*将所有菜单的parentid和传递的菜单id对比，相等就递归调用，并且加到treeList中，用于setChildren*/
            if (menu.getParent() != null) {/*去除不是一级菜单的菜单*/
                if (id.equals(menu.getParent().getId())) {/*可能出现空指的放在后面*/
                    menu.setChildren(getMenuTreeMenu(menuList, menu.getId()));
                    treeList.add(menu);
                }
            }
        }
        return treeList;
    }

    /**
     * 获取一个子菜单所有的父菜单
     *
     * @return
     */
    @Override
    public List<String> findAllParent(Menu menu) {
        List<String> menuListParent = new ArrayList<>();
        Menu updateMenu = menuMapper.findOne(menu.getId());/*修改的菜单*/
        getPartnt(updateMenu, menuListParent);
        return menuListParent;
    }

    /**
     * 递归查找所有的父菜单,装到list中
     *
     * @param list
     */
    public void getPartnt(Menu updateMenu, List<String> list) {
        Menu parent = updateMenu.getParent();
        if (parent != null) {
            list.add(parent.getName());
            getPartnt(parent, list);
        }

    }

    @Override
    public List<Long> findAllParent(Long id) {
        List<Long> menuListParent = new ArrayList<>();
        Menu updateMenu = menuMapper.findOne(id);/*修改的菜单*/
        getPartntReturnMenu(updateMenu, menuListParent);
        Collections.reverse(menuListParent);/*翻转元素，让第一级菜单在第一个位置*/
        return menuListParent;
    }

    /**
     * 递归查找所有的父菜单,装到list中
     *
     * @param list
     */
    public void getPartntReturnMenu(Menu updateMenu, List<Long> list) {
        Menu parent = updateMenu.getParent();
        if (parent != null) {
            list.add(parent.getId());
            getPartntReturnMenu(parent, list);
        }

    }


    @Override
    public Menu findByName(String name) {
        List<Menu> menuList = menuMapper.findByName(name);
        if (menuList.size() > 0) {
            return menuList.get(0);
        }
        return null;
    }

    @Override
    public List<Menu> findMenuByEmployeeId(Long id) {
        List<Menu> firstMenuList = new ArrayList<>();/*用于装一级菜单*/
        List<Menu> menuByEmployeeIdList = menuMapper.findMenuByEmployeeId(id);/*查询用户有权限的菜单*/
        for (Menu menuByEmployeeId : menuByEmployeeIdList) {/*数据不能重复，需要去重复，sql或写逻辑代码，这里用了sql*/
            getPermissionnMenuDg(menuByEmployeeId, firstMenuList);
        }
        return firstMenuList;
    }


    public void getPermissionnMenuDg(Menu menuByEmployeeId, List<Menu> firstMenuList) {
        Menu parent = menuByEmployeeId.getParent();
        if (parent != null) {
            if (!parent.getChildren().contains(menuByEmployeeId)) {/*不添加重复的*/
                parent.getChildren().add(menuByEmployeeId);
            }
            getPermissionnMenuDg(parent, firstMenuList);/*递归了*/
        } else {
            if (!firstMenuList.contains(menuByEmployeeId)) {/*这里解决两个子菜单拥有用一个父菜单的情况*/
                firstMenuList.add(menuByEmployeeId);/*一直递归到没有父菜单为止*/
            }
        }
    }

    /**
     * 查询所有最后一级菜单
     *
     * @return
     */
    @Override
    public List<Menu> findMenuItem() {
        List<Menu> menuItemList = new ArrayList<>();
        List<Menu> allMenu = findAll();/*得到所有菜单的数据已经是有children的数据了*/
        getLastMenuItem(allMenu, menuItemList);
        return menuItemList;
    }

    public void getLastMenuItem(List<Menu> menuList, List<Menu> menuItemList) {
        for (Menu menu : menuList) {
            if (menu.getChildren().size() > 0) {
                getLastMenuItem(menu.getChildren(), menuItemList);
            } else {
                menuItemList.add(menu);
            }

        }
    }

    /**
     * 角色和菜单配置是的需要的回显菜单数据
     *
     * @param ids
     * @return
     */
    @Override
    public List<Menu> findAllMenunewTreeDate(Long[] ids) {
        ArrayList<Menu> firstMenuList = new ArrayList<>();
        for (Long id : ids) {
            Menu one = menuMapper.findOne(id);
            getPermissionnMenushow(one, firstMenuList);
        }
        return firstMenuList;
    }


    public void getPermissionnMenushow(Menu menuByEmployeeId, List<Menu> firstMenuList) {
        Menu parent = menuByEmployeeId.getParent();
        if (parent != null) {
            if (parent.getChildren().contains(menuByEmployeeId)) {
                Menu menu = parent.getChildren().get(parent.getChildren().indexOf(menuByEmployeeId));
                for (Menu child : menuByEmployeeId.getChildren()) {
                    if (!menu.getChildren().contains(child)){
                        menu.getChildren().add(child);
                    }
                }
            }else {
                parent.getChildren().add(menuByEmployeeId);
            }
            getPermissionnMenushow(parent, firstMenuList);/*递归了*/
        } else {
            if (firstMenuList.contains(menuByEmployeeId)) {/*这里解决两个子菜单拥有用一个父菜单的情况*/
                Menu menu = firstMenuList.get(firstMenuList.indexOf(menuByEmployeeId));
                for (Menu child : menuByEmployeeId.getChildren()) {
                    if (!menu.getChildren().contains(child)){
                        menu.getChildren().add(child);
                    }
                }
            }else {
                firstMenuList.add(menuByEmployeeId);/*一直递归到没有父菜单为止*/
            }
        }
    }

    @Override
    public List<Menu> findAllRolePermissionMenuByRoleId(Long id) {
        List<Menu> firstMenuList = new ArrayList<>();/*用于装一级菜单*/
        List<Menu> menuByRoleIdIdList = menuMapper.findAllRolePermissionMenuByRoleId(id);/*查询用户有权限的菜单*/
        for (Menu menuByRoleId : menuByRoleIdIdList) {/*数据不能重复，需要去重复，sql或写逻辑代码，这里用了sql*/
            getPermissionnMenuByRole(menuByRoleId, firstMenuList);
        }
        return firstMenuList;
    }

    public void getPermissionnMenuByRole(Menu menuByEmployeeId, List<Menu> firstMenuList) {
        Menu parent = menuByEmployeeId.getParent();
        if (parent != null) {
            if (!parent.getChildren().contains(menuByEmployeeId)) {
                parent.getChildren().add(menuByEmployeeId);
            }
            getPermissionnMenuByRole(parent, firstMenuList);/*递归了*/
        } else {
            if (!firstMenuList.contains(menuByEmployeeId)) {/*这里解决两个子菜单拥有用一个父菜单的情况*/
                firstMenuList.add(menuByEmployeeId);/*一直递归到没有父菜单为止*/
            }
        }
    }

    /**
     * 角色设置页面后，处理当前数据并且获取其所有的最后一级菜单
     * @param ids
     * @return
     */
    @Override
    public List<Menu> getLastMenuByRoleSave(Long[] ids) {
        List<Menu> menuItemList = new ArrayList<>();
        List<Menu> allMenunewTreeDate = findAllMenunewTreeDate(ids);/*获得了保存的权限页面的树形数据*/
        getLastMenuItem(allMenunewTreeDate, menuItemList);
        return menuItemList;
    }
}
