package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.mapper.CustomerMapper;
import cn.nine.crm.query.CustomerQuery;
import cn.nine.crm.service.ICustomerService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Customer)表Service层接口
 *
 * @author 申林
 * @since 2020-05-29 22:15:35
 */
 @Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer,Long,CustomerQuery> implements ICustomerService{

    private CustomerMapper customerMapper;

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }
}