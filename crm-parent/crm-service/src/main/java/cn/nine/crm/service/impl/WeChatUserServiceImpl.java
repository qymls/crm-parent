package cn.nine.crm.service.impl;

import cn.nine.crm.domain.WeChatUser;
import cn.nine.crm.mapper.WeChatUserMapper;
import cn.nine.crm.service.IWeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信登录
 * */
@Service
public class WeChatUserServiceImpl implements IWeChatUserService {
    private  WeChatUserMapper weChatUserMapper;

    @Autowired
    public void setWeChatUserMapper(WeChatUserMapper weChatUserMapper) {
        this.weChatUserMapper = weChatUserMapper;
    }

    @Override
    public WeChatUser selectByOpenId(String openId) {
        return weChatUserMapper.selectByOpenId(openId);
    }

    @Override
    public void insert(WeChatUser weChatUser) {
        weChatUserMapper.insert(weChatUser);
    }

    @Override
    public void update(WeChatUser weChatUser) {
       weChatUserMapper.update(weChatUser);
    }
}
