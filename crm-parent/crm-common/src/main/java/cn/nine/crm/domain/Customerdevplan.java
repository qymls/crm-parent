package cn.nine.crm.domain;

import java.util.Date;

import lombok.Data;

/**
 * (Customerdevplan)实体类
 *
 * @author 申林
 * @since 2020-05-30 21:13:33
 */
@Data
public class Customerdevplan extends BaseDomain {
    /*计划时间*/
    private Date palntime;
    /*计划主题*/
    private String plansubject;
    /*计划内容*/
    private String plandetails;
    /*计划实施方式*/
    private String plantype;
    /*潜在客户*/
    private Customer customer;
    /*创建人*/
    private Employee inputuser;
    /*创建时间*/
    private Date inputtime;
    /*所属租户*/
    private Tenant tenant;


}
