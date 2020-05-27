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
import java.util.List;

/**
 * 订金订单 ServiceImpl层
 */
@Service
@Transactional
public class OrdersServiceImpl extends BaseServiceImpl<Orders,Long, OrdersQuery> implements IOrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    public void save(Orders orders) {
        String a = "D";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(new Date());
        int nf = 100001;
        nf = nf++;
        String autoNum = a+str+nf;
        //判断数据库中是否有该编号
        List<Orders> list = ordersMapper.findAll();
        orders.setSn(autoNum);
        ordersMapper.save(orders);
    }
}
