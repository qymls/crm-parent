package cn.nine.crm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信登录信息实体
 * */
@EqualsAndHashCode(callSuper = false)
@Data
public class WeChatUser extends BaseDomain {

    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String privilege;
    private String unionid;
    private Long empId;

}
