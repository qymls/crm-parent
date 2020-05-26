package cn.nine.crm.domain;

import lombok.Data;

import java.util.Date;

/**
 * 保修单明细
 */
@Data
public class GuaranteeItem extends BaseDomain {

    //未处理
    public static final Integer STATUS_UNPROCESS = 0;
    //已处理
    public static final Integer STATUS_PROCESSED = 1;
    //正在处理
    public static final Integer STATUS_INPROCESS = 2;

    //所属保修单号
    private String guaranteeSn;

    //保修时间
    private Date date;

    //保修内容
    private String details;

    //保修状态：0未处理 1已处理 2正在处理
    private Integer status = STATUS_UNPROCESS;

}