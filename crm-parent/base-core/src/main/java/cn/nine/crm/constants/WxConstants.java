package cn.nine.crm.constants;

public class WxConstants {
    public  final static String APPID = "wxd853562a0548a7d0";

    //用户授权后微信的回调域名
    public final static String CALLBACK="http://bugtracker.itsource.cn/weixin/QRCodeCallBack";

    public final static String SCOPE = "snsapi_login";

    public final static String APPSECRET = "4a5d5615f93f24bdba2ba8534642dbb6";

    public final static String STATE = "CRMState";


    //微信上获取code的地址
    public final static String CODEURL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    //微信上获取at的地址
    public final static String ACCESSTOKEN_BASEURL= "https://api.weixin.qq.com/sns/oauth2/access_token";
    public final static String ACCESSTOKE_PARAMS = "appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    //微信上获取用户信息的地址
    public final static String USERINFO_BASEURL = "https://api.weixin.qq.com/sns/userinfo" ;
    public final static String USERINFO_PARAMS = "access_token=%s&openid=%s";

}
