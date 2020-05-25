package cn.nine.crm.service.impl;

import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.query.ContractItemQuery;
import cn.nine.crm.service.ContractItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractItemServiceImpl extends BaseServiceImpl<ContractItem,Long, ContractItemQuery> implements ContractItemService {
}
