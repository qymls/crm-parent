package cn.nine.crm.web.controller;

import cn.nine.crm.service.pictureutil.IPictureService;
import cn.nine.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private IPictureService pictureService;

    @Autowired
    public void setPictureService(IPictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/tenant",method = RequestMethod.POST)
    @ResponseBody
    public Result upload(MultipartFile file, String headImage, HttpServletRequest request) throws Exception {
        String image = pictureService.saveImage(file, headImage, request);
        return Result.ok(image);
    }

    @RequestMapping("/deleteImg")
    @ResponseBody
    public Boolean deleteImg(String path, HttpServletRequest req) {
        return pictureService.deleteImage(path, req);
    }

}
