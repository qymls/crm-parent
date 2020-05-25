package cn.nine.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 孟俊江
 * 员工实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseDomain{

    private String username;
    private String password;
    //真实姓名
    private String realName;
    private String tel;
    private String email;
    private Date inputTime;
    //状态
    private Integer state;
    //所属部门
    private Department department;
    //盐值
    private String salt;
    //所属租户
    private Long tenant;

    private Long type;
}
