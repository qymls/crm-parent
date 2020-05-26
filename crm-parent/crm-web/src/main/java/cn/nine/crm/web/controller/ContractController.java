package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.query.ContractQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 合同 Controller 层
 */
@Controller
@ResponseBody
@RequestMapping("/contract")
public class ContractController extends BaseController<Contract,Long, ContractQuery>{

}
