package cn.nine.crm.domain;

import lombok.Data;

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
    //合同客户 显示客户姓名 编辑时选择
    private Customer customer;
    //签订合同的时间
    private Date signTime;
    //营销人员 显示姓名 添加时默认为登录状态时的用户名  需要有权限 该项是否能更改
    private Employee seller;
    //合同总金额  单位：元
    private BigDecimal totalAmount;
    //合同摘要  编辑添加设置为可拖动打开文本框
    private String intro;
    //合同订单明细数据对象--付款明细  按钮  点击 打开表格收缩的合同明细数据
    private ContractItem contractitem;
    //所属租户 设置下拉框选择
    private Tenant tenant;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
