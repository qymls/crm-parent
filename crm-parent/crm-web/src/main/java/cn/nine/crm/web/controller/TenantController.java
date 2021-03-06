package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Tenant;
import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.query.TenantQuery;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.pictureutil.IPictureService;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * (Tenant)表Controller
 *
 * @author ReX
 * @since 2020-05-28 14:55:23
 */
@Controller
@RequestMapping("/tenant")
@SuppressWarnings(value = "all")/*抑制警告*/
public class TenantController extends BaseController<Tenant,Long, TenantQuery>{

    private ITenantService tenantService;

    @Autowired
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

}
