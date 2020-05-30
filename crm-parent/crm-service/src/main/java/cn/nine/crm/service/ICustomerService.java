package cn.nine.crm.service;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.query.CustomerQuery;

import java.util.List;

/**
 * (Customer)表Service层接口
 *
 * @author 申林
 * @since 2020-05-29 22:15:34
 */
public interface ICustomerService extends IBaseService<Customer,Long,CustomerQuery>{

    void updateStateByid(Boolean state,Long id);

    List<Customer> getdevCustomer(String status);
}
