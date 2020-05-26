package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.query.ContractQuery;
import cn.nine.crm.service.IContractService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 合同 ServiceImpl层
 */
@Service
@Transactional
public class ContractServiceImpl extends BaseServiceImpl<Contract,Long, ContractQuery> implements IContractService {
}
