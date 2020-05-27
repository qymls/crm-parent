package cn.nine.crm.web.controller;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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

}
