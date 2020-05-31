package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Customerdevplan;
import cn.nine.crm.mapper.CustomerdevplanMapper;
import cn.nine.crm.query.CustomerdevplanQuery;
import cn.nine.crm.service.ICustomerdevplanService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Customerdevplan)表Service层接口
 *
 * @author 申林
 * @since 2020-05-30 21:13:33
 */
 @Service
public class CustomerdevplanServiceImpl extends BaseServiceImpl<Customerdevplan,Long,CustomerdevplanQuery> implements ICustomerdevplanService{

    private CustomerdevplanMapper customerdevplanMapper;

    @Autowired
    public void setCustomerdevplanMapper(CustomerdevplanMapper customerdevplanMapper) {
        this.customerdevplanMapper = customerdevplanMapper;
    }
}