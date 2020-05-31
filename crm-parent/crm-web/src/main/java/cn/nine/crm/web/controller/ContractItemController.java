package cn.nine.crm.web.controller;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
import cn.nine.crm.service.IContractItemService;
import cn.nine.crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
    合同明细 Controller 层
*/
@Controller
@ResponseBody
@RequestMapping("/contractitem")
@CrossOrigin
public class ContractItemController extends BaseController<ContractItem,Long, ContractItemQuery>{

    @Autowired
    private IContractItemService contractItemService;
    @RequestMapping("/findByContractSn/{ContractSn}")
    public Result findByContractSn(@PathVariable("ContractSn") String ContractSn){
        try {
            ContractItem contractSn = contractItemService.findByContractSn(ContractSn);
            return Result.ok(contractSn);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器异常");
        }

    }
}
