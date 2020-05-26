package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Menu;
import cn.nine.crm.mapper.MenuMapper;
import cn.nine.crm.query.MenuQuery;
import cn.nine.crm.service.IMenuService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Menu)表Service层接口
 *
 * @author 申林
 * @since 2020-05-24 11:30:22
 */
 @Service
public class MenuServiceImpl extends BaseServiceImpl<Menu,Long,MenuQuery> implements IMenuService{

    private MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

}
