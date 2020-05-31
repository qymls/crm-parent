package cn.nine.crm.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class WxLoginToken extends UsernamePasswordToken {

    public  WxLoginToken(String username){
        super(username,"");
    }
}
