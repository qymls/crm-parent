package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Order;
import cn.nine.crm.query.OrderQuery;
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
public class OrderController extends BaseController<Order,Long, OrderQuery>{

}
