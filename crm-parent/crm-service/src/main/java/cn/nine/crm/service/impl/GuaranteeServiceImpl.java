package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Guarantee;
import cn.nine.crm.mapper.GuaranteeMapper;
import cn.nine.crm.query.GuaranteeQuery;
import cn.nine.crm.service.IGuaranteeService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuaranteeServiceImpl
        extends BaseServiceImpl<Guarantee, Long, GuaranteeQuery>
        implements IGuaranteeService {

    private GuaranteeMapper guaranteeMapper;

    @Autowired
    public void setGuaranteeMapper(GuaranteeMapper guaranteeMapper) {
        this.guaranteeMapper = guaranteeMapper;
    }

}
