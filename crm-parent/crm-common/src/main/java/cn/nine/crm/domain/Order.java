package cn.nine.crm.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单合同管理--订单 实体类
 * 租户公司的客户交了订金后，需要签订定金订单
 */
@Data
public class Order extends BaseDomain {

    //订单编号 自动生成
    private String sn;
    //客户对象 显示客户姓名
    //private Customer customer;
    //签订时间 前端选择
    private Date signTime;
    //营销人员 前端选择 显示人员姓名
    private Employee seller;
    //订定金额 单位 元
    private BigDecimal amount;
    //摘要
    private String intro;
    //租户对象 显示租户的公司名称
    private Long tenant;



}
