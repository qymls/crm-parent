package cn.nine.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务管理-合同实体类
 * 租户的客户交了定金后，并确定要购买产品,则需要签订购买合同
 */
@Data
public class Contract extends BaseDomain{
    //合同编号 自动生成
    private String sn;
    //合同客户 显示客户姓名
    private Customer customer;
    //签订合同的时间
    private Date signTime;
    //营销人员 显示姓名
    private Employee seller;
    //合同总金额  单位：元
    private BigDecimal totalAmount;
    //合同摘要
    private String intro;
    //合同订单明细数据对象--付款明细  按钮  点击 进入合同明细表
    private ContractItem contractitem;
    //所属租户
    private Tenant tenant;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date getSignTime() {
        return signTime;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
