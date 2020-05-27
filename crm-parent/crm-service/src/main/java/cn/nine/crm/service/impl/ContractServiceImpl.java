package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.mapper.ContractMapper;
import cn.nine.crm.query.ContractQuery;
import cn.nine.crm.service.IContractService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 合同 ServiceImpl层
 */
@Service
@Transactional
public class ContractServiceImpl extends BaseServiceImpl<Contract,Long, ContractQuery> implements IContractService {

    @Autowired
    private ContractMapper contractMapper;

    public void save(Contract contract) {
        String a = "D";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(new Date());
        int nf = 100001;
        nf = nf++;
        String autoNum = a+str+nf;
        //判断数据库中是否有该编号
        List<Contract> list = contractMapper.findAll();
        contract.setSn(autoNum);
        contractMapper.save(contract);
    }

}
