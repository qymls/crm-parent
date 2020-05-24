package cn.nine.crm.web.controller;

import cn.nine.crm.dto.IdsDto;
import cn.nine.crm.service.BaseService;
import cn.nine.crm.utils.JSONResult;
import cn.nine.crm.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@ResponseBody
@CrossOrigin
public class BaseController<T, ID extends Serializable, Q> {

    @Autowired
    private BaseService<T, ID, Q> baseService;

    //查找所有数据1
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JSONResult selectAll() {
        try {
            List<T> list = baseService.selectAll();
            return JSONResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }

    //根据id查找数据
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public JSONResult selectById(@PathVariable("id") ID id) {
        try {
            T t = baseService.selectById(id);
            return JSONResult.ok(t);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }

    //分页查询数据
    @RequestMapping(value = "/selectForPage", method = RequestMethod.POST)
    public JSONResult selectForPage(@RequestBody Q q) {
        try {
            PageList<T> pageList = baseService.selectForPage(q);
            return JSONResult.ok(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }

    //新增数据
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONResult add(@RequestBody T t) {
        try {
            baseService.insert(t);
            return JSONResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }

    //编辑数据
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public JSONResult edit(@RequestBody T t) {
        try {
            baseService.update(t);
            return JSONResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }
    //删除数据
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") ID id){
        try{
            baseService.delete(id);
            return JSONResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }
    //批量删除数据
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    public JSONResult delete(@RequestBody IdsDto dto){
        try{
            baseService.batchDelete(dto.getIds());
            return JSONResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.error("服务器异常!");
        }
    }
}
