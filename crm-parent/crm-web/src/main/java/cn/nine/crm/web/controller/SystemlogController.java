package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Systemlog;
import cn.nine.crm.query.SystemlogQuery;
import cn.nine.crm.service.ISystemlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * (Systemlog)表Controller
 *
 * @author 申林
 * @since 2020-05-28 20:20:50
 */
@Controller
@RequestMapping("systemlog")
@SuppressWarnings(value = "all")/*抑制警告*/
public class SystemlogController extends BaseController<Systemlog,Long, SystemlogQuery>{
    private ISystemlogService systemlogService;

    @Autowired
    public void setSystemlogService(ISystemlogService systemlogService) {
        this.systemlogService = systemlogService;
    }
}