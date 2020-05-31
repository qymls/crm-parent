package cn.nine.crm.web.controller;

import cn.nine.crm.dto.TenantSettleInDto;
import cn.nine.crm.service.ITenantService;
import cn.nine.crm.service.pictureutil.IPictureService;
import cn.nine.crm.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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

@Controller
@RequestMapping("settleIn")
public class SettleInController {
    private ITenantService tenantService;

    @Autowired
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    private IPictureService pictureService;

    @Autowired
    public void setPictureService(IPictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/settleIn")
    @ApiOperation(value = "保存一个实体")
    @ResponseBody
    public Result save(@RequestBody TenantSettleInDto dto) {
        try {
            tenantService.settleIn(dto);//添加
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result upload(MultipartFile file, HttpServletRequest request) throws Exception {
        String image = pictureService.saveImage(file, "", request);
        return Result.ok(image);
    }

}
