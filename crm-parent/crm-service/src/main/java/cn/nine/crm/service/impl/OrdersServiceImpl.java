package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Orders;
import cn.nine.crm.mapper.OrdersMapper;
import cn.nine.crm.query.OrdersQuery;
import cn.nine.crm.service.IOrdersService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订金订单 ServiceImpl层
 */
@Service
@Transactional
public class OrdersServiceImpl extends BaseServiceImpl<Orders,Long, OrdersQuery> implements IOrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    public void save(Orders orders) {
        //设置订单开头字符
        String a = "D";
        //中间数字以订单建立当天的日期即毫秒数
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(new Date());
        orders.setSn(a+str);
        ordersMapper.save(orders);
    }
}
