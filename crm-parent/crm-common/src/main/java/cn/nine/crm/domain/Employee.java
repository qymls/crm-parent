package cn.nine.crm.domain;

import cn.nine.crm.util.BaseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inputTime;
    //状态 默认在职
    private Integer state = BaseConstant.STATUS_WORK;
    //所属部门
    private Department department;
    //盐值
    private String salt;
    //所属租户
    private Tenant tenant;
    //关联角色
    private List<Role> role = new ArrayList<>();
}
