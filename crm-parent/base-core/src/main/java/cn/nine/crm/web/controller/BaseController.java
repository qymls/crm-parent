package cn.nine.crm.web.controller;

import cn.nine.crm.dto.IdsDto;
import cn.nine.crm.service.IBaseService;
import cn.nine.crm.util.PageList;
import cn.nine.crm.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@ResponseBody
@SuppressWarnings(value = "all")/*抑制警告*/
@Api(value = "BaseController")
public class BaseController<T, ID extends Serializable, Q> {
    @Autowired
    private IBaseService<T, ID, Q> baseService;

    //查询所有
    @ApiOperation(value = "查询所有")
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<T> tList = baseService.findAll();
            return Result.ok(tList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }
    }


    //查询分页列表
    @PostMapping("selectForPage")
    @ApiOperation(value = "分页条件查询")
    public Result selectForPage(@RequestBody Q query) {
        try {
            PageList pageList = baseService.selectForPage(query);
            return Result.ok(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }

    //查找单个
    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据主键id查询一个实体")
    public Result findOne(@PathVariable("id") ID id) {
        try {
            T one = baseService.findOne(id);
            return Result.ok(one);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }

    //添加
    @PostMapping("/save")
    @ApiOperation(value = "保存一个实体")
    public Result save(@RequestBody T t) {
        try {
            baseService.save(t);        //添加
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }

    //修改
    @PostMapping("/update")
    @ApiOperation(value = "修改一个实体")
    public Result update(@RequestBody T t) {
        try {
            baseService.update(t);   //修改
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }

    //删除
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据主键id删除一个实体")
    public Result delete(@PathVariable("id") ID id) {
        try {
            baseService.delete(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }
    }

    //删除
    @PostMapping("/batchDelete")
    @ApiOperation(value = "根据主键批量删除")
    public Result batchDelete(@RequestBody IdsDto idsDto) {
        try {
            baseService.batchDelete(idsDto.getIds());
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }
}
