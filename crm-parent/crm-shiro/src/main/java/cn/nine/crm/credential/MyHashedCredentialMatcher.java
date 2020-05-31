package cn.nine.crm.credential;

import cn.nine.crm.token.WeChatToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyHashedCredentialMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token instanceof WeChatToken) {
            return true;
        }
        return super.doCredentialsMatch(token, info);
    }
}
