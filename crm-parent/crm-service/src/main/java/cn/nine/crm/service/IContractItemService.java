package cn.nine.crm.service;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
/**
 * 合同明细 Service层
 */
public interface IContractItemService extends IBaseService<ContractItem,Long, ContractItemQuery> {
    //通过合同编号查询合同明细
    ContractItem findByContractSn(String contractSn);
}
