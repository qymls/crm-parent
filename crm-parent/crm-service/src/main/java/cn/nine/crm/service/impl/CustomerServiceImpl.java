package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer,Long,CustomerQuery>
        implements ICustomerService {

}
