package cn.nine.crm.mapper;


import cn.nine.crm.domain.WeChatUser;

/**
 * 微信登录
 * */
public interface WeChatUserMapper {

    WeChatUser selectByOpenId(String openId);

    void insert(WeChatUser weChatUser);

    void update(WeChatUser weChatUser);
}
