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

/**
 * 合同 ServiceImpl层
 */
@Service
@Transactional
public class ContractServiceImpl extends BaseServiceImpl<Contract,Long, ContractQuery> implements IContractService {

    @Autowired
    private ContractMapper contractMapper;

    public void save(Contract contract) {
        //设置订单开头字符
        String a = "C";
        //中间数字以订单建立当天的日期及毫秒数
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(new Date());
        contract.setSn(a+str);
        contractMapper.save(contract);
    }

}
