package cn.nine.crm.mapper;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;

/**
 * 合同明细 mapper层
 */
public interface ContractItemMapper extends BaseMapper<ContractItem,Long, ContractItemQuery>{
    //通过合同编号查询合同明细
    ContractItem findByContractSn(String contractSn);
}
