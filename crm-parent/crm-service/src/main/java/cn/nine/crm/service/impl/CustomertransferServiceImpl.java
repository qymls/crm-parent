package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Customertransfer;
import cn.nine.crm.mapper.CustomertransferMapper;
import cn.nine.crm.query.CustomertransferQuery;
import cn.nine.crm.service.ICustomertransferService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Customertransfer)表Service层接口
 *
 * @author 申林
 * @since 2020-05-31 00:40:32
 */
 @Service
public class CustomertransferServiceImpl extends BaseServiceImpl<Customertransfer,Long,CustomertransferQuery> implements ICustomertransferService{

    private CustomertransferMapper customertransferMapper;

    @Autowired
    public void setCustomertransferMapper(CustomertransferMapper customertransferMapper) {
        this.customertransferMapper = customertransferMapper;
    }
}