package cn.nine.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    //所属保修单
    private String guaranteeSn;

    //保修时间【填写日期】
    private Date inputDate = new Date();

    //保修内容
    private String details;

    //保修状态：0未处理 1已处理 2正在处理
    private Integer status = STATUS_UNPROCESS;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date getInputDate() {
        return inputDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }
}