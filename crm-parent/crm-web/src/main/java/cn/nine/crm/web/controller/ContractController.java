package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.query.ContractQuery;
import cn.nine.crm.util.LogAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 合同 Controller 层
 */
@Controller
@ResponseBody
@RequestMapping("/contract")
@CrossOrigin
@LogAnnotations
public class ContractController extends BaseController<Contract,Long, ContractQuery>{

}
