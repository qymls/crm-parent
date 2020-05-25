package cn.nine.crm.service.impl;

import cn.nine.crm.domain.GuaranteeItem;
import cn.nine.crm.mapper.GuaranteeItemMapper;
import cn.nine.crm.query.GuaranteeItemQuery;
import cn.nine.crm.service.IGuaranteeItemService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuaranteeItemServiceImpl
        extends BaseServiceImpl<GuaranteeItem, Long, GuaranteeItemQuery>
        implements IGuaranteeItemService {

    private GuaranteeItemMapper guaranteeItemMapper;

    @Autowired
    public void setGuaranteeMapper(GuaranteeItemMapper guaranteeItemMapper) {
        this.guaranteeItemMapper = guaranteeItemMapper;
    }

}
