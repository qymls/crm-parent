package cn.nine.crm.service;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
/**
 * 合同明细 Service层
 */
public interface IContractItemService extends IBaseService<ContractItem,Long, ContractItemQuery> {
    //合同订单生成时，自动生成一张合同明细表
    int saveByContract(ContractItem contractItem);
}
