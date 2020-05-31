package cn.nine.crm.token;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 微信登录token，自定义usernamePasswordToken
 * */

public class WeChatToken extends UsernamePasswordToken {

    public WeChatToken(String username) {
        super(username,"");
    }

}
