package cn.nine.crm.web.controller;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/contractitem")
public class ContractItemController extends BaseController<ContractItem,Long, ContractItemQuery>{

}
