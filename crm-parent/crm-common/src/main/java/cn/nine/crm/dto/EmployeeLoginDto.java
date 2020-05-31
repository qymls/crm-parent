package cn.nine.crm.dto;

import lombok.Data;

@Data
public class EmployeeLoginDto {
    /*openid*/
    private String openId;
    /*用户名*/
    private String username;
    /*密码*/
    private String password;
}
