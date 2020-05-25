package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Order;
import cn.nine.crm.query.OrderQuery;
import cn.nine.crm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Order,Long, OrderQuery> implements OrderService {
}
