package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.query.CustomerQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 合同 Controller 层
 */
@Controller
@ResponseBody
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController extends BaseController<Customer,Long, CustomerQuery>{

}
