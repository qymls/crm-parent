package cn.nine.crm.domain;
import java.util.Date;
import lombok.Data;
/**
 * (Customer)实体类
 *
 * @author 申林
 * @since 2020-05-29 22:15:22
 */
@Data
public class Customer extends BaseDomain{
    /*客户姓名*/
    private String name;
    /*客户年龄*/
    private Integer age;
    /*客户性别*/
    private Boolean sex;
    /*电话号码*/
    private String tel;
    /*邮箱*/
    private String email;
    /*QQ*/
    private String qq;
    /*微信*/
    private String wechat;
    /*营销人员*/
    private Employee seller;
    /*职业*/
    private String job;
    /*收入水平*/
    private String salarylevel;
    /*客户来源*/
    private String customersource;
    /*创建人*/
    private Employee inputuser;
    /*创建时间*/
    private Date inputtime;
    /*所属租户*/
    private Tenant tenant;
    /*成功率*/
    private Double successrate;
    /*备注*/
    private String remark;
    /**
    * 是否为潜在客户
    */
    private String status;
    /**
    * 启用禁用
    */
    private Boolean state;



}
