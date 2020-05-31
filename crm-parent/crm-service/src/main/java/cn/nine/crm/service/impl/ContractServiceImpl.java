package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Contract;
import cn.nine.crm.domain.ContractItem;
import cn.nine.crm.mapper.ContractItemMapper;
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
    @Autowired
    private ContractItemMapper contractItemMapper;

    //添加合同编号自动生成功能
    public void save(Contract contract) {
        //设置订单开头字符
        String a = "C";
        //中间数字以订单建立当天的日期及毫秒数
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(new Date());
        contract.setSn(a+str);
        contractMapper.save(contract);

        Contract contract01 = contractMapper.findOne(contract.getId());

        //如果调用contract中save方法，则在contractitem 表中创建一条新的记录
        ContractItem contractItem = new ContractItem();
        contractItem.setContract(contract01);
        contractItem.setContractSn(contract01.getSn());
        //默认合同明细表的付款时间==合同的签订时间
        contractItem.setPayTime(contract01.getSignTime());
        //默认合同明细表的总金额==合同的总金额
        contractItem.setPayMoney(contract01.getTotalAmount());
        //默认合同明细表的付款占额==100
        contractItem.setScale("100");
        contractItemMapper.save(contractItem);

    }



}
