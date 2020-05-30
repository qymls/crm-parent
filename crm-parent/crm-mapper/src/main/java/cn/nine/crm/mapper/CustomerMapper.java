package cn.nine.crm.mapper;

import cn.nine.crm.domain.Customer;
import cn.nine.crm.query.CustomerQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Customer)表数据库访问层
 *
 * @author 申林
 * @since 2020-05-29 22:15:26
 */
public interface CustomerMapper extends BaseMapper<Customer,Long,CustomerQuery>{

    void updateStateByid(@Param("state")Boolean state,@Param("id") Long id);

    List<Customer> getdevCustomer(String status);
}
