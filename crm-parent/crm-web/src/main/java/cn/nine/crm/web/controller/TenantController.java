package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.TenantQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tenant")
public class TenantController extends BaseController<Tenant,Long, TenantQuery> {
}
