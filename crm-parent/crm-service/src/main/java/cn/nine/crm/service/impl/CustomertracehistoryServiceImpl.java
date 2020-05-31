package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Customertracehistory;
import cn.nine.crm.mapper.CustomertracehistoryMapper;
import cn.nine.crm.query.CustomertracehistoryQuery;
import cn.nine.crm.service.ICustomertracehistoryService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Customertracehistory)表Service层接口
 *
 * @author 申林
 * @since 2020-05-30 23:01:04
 */
 @Service
public class CustomertracehistoryServiceImpl extends BaseServiceImpl<Customertracehistory,Long,CustomertracehistoryQuery> implements ICustomertracehistoryService{

    private CustomertracehistoryMapper customertracehistoryMapper;

    @Autowired
    public void setCustomertracehistoryMapper(CustomertracehistoryMapper customertracehistoryMapper) {
        this.customertracehistoryMapper = customertracehistoryMapper;
    }
}