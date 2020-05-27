package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Orders;
import cn.nine.crm.query.OrdersQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定金订单 Controller 层
 */
@Controller
@ResponseBody
@RequestMapping("/order")
@CrossOrigin
public class OrdersController extends BaseController<Orders,Long, OrdersQuery>{

}
