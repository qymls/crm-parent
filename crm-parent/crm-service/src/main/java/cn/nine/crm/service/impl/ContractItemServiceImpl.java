package cn.nine.crm.service.impl;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.mapper.ContractItemMapper;
import cn.nine.crm.query.ContractItemQuery;
import cn.nine.crm.service.IContractItemService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 合同明细 ServiceImpl层
 */
@Service
public class ContractItemServiceImpl extends BaseServiceImpl<ContractItem,Long, ContractItemQuery> implements IContractItemService {

    @Autowired
    private ContractItemMapper contractItemMapper;
    //合同明细表自动生成
    @Override
    public int saveByContract(ContractItem contractItem) {
        return contractItemMapper.saveByContract(contractItem);
    }
}
