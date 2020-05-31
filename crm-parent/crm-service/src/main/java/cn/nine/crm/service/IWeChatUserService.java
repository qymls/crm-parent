package cn.nine.crm.service;


import cn.nine.crm.domain.WeChatUser;

/**
 * 微信登录
 * */
public interface IWeChatUserService {

   WeChatUser selectByOpenId(String openId);

   void insert(WeChatUser weChatUser);

   void update(WeChatUser weChatUser);
}
