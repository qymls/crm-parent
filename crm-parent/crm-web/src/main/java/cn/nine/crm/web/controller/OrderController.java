package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Order;
import cn.nine.crm.query.OrderQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/order")
public class OrderController extends BaseController<Order,Long, OrderQuery>{

}
