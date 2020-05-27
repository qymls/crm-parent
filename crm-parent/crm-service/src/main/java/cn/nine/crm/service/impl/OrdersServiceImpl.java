package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Orders;
import cn.nine.crm.query.OrdersQuery;
import cn.nine.crm.service.IOrdersService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订金订单 ServiceImpl层
 */
@Service
@Transactional
public class OrdersServiceImpl extends BaseServiceImpl<Orders,Long, OrdersQuery> implements IOrdersService {
}
