package cn.nine.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务管理-合同订单明细实体类
 * 合同订单中点击明细表  跳转到该类对应的表
 * 一个合同订单 可对应多个合同明细单
 */
@Data
public class ContractItem extends BaseDomain {
    //未支付
    public static final Integer PAY_NO = 0;
    //已支付
    public static final Integer PAY_YES = 1;

    //支付完成
    public static final Integer PAY_END = 2;
    //未完成 结束中
    public static final Integer PAY_ENDING = 3;

    //所属合同 通过合同订单id关联
    private Contract contract;
    //所属合同关联id
    private Long contract_id;
    //付款时间
    private Date payTime;
    //付款金额 自客户合同项目成功履行后，开始计时1年
    //可根据比例自动计算  可一次性支付或分批次支付
    private BigDecimal payMoney;
    //所占比例 100% 本次支付占总需要支付金额的比例 不加定金金额
    private String scale;
    //本次是否支付  0--待支付  1--已支付
    private Integer isPayment = PAY_NO;
    //合同金额是否结算完成
    private Integer pay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getPayTime() {
        return payTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
