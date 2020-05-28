package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tenant")
public class TenantController extends BaseController<Tenant,Long, TenantQuery> {

    private ITenantService tenantService;

    @Autowired
    public void setMenuService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/settleIn")
    @ApiOperation(value = "保存一个实体")
    public Result save(@RequestBody T t) {
        try {
            //tenantService.settleIn();        //添加
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }
}
