package cn.nine.crm.credential;

import cn.nine.crm.token.WxLoginToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //微信登录：不需要密码
        if(token instanceof WxLoginToken){
            return true;
        }
        //正常登录：需要密码
        return super.doCredentialsMatch(token, info);
    }
}
