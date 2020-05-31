package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Systemdictionaryitem;
import cn.nine.crm.query.SystemdictionaryitemQuery;
import cn.nine.crm.service.ISystemdictionaryitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * (Systemdictionaryitem)表Controller
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
@Controller
@RequestMapping("systemdictionaryitem")
@SuppressWarnings(value = "all")/*抑制警告*/
public class SystemdictionaryitemController extends BaseController<Systemdictionaryitem,Long, SystemdictionaryitemQuery>{
    private ISystemdictionaryitemService systemdictionaryitemService;

    @Autowired
    public void setSystemdictionaryitemService(ISystemdictionaryitemService systemdictionaryitemService) {
        this.systemdictionaryitemService = systemdictionaryitemService;
    }
}