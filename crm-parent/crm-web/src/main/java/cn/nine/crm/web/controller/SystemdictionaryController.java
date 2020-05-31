package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Systemdictionary;
import cn.nine.crm.query.SystemdictionaryQuery;
import cn.nine.crm.service.ISystemdictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * (Systemdictionary)表Controller
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
@Controller
@RequestMapping("systemdictionary")
@SuppressWarnings(value = "all")/*抑制警告*/
public class SystemdictionaryController extends BaseController<Systemdictionary,Long, SystemdictionaryQuery>{
    private ISystemdictionaryService systemdictionaryService;

    @Autowired
    public void setSystemdictionaryService(ISystemdictionaryService systemdictionaryService) {
        this.systemdictionaryService = systemdictionaryService;
    }
}