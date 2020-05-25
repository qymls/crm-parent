package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.query.ContractQuery;
import cn.nine.crm.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractServiceImpl extends BaseServiceImpl<Contract,Long, ContractQuery> implements ContractService {
}
