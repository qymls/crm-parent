package cn.nine.crm.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 保修单管理
 */
@Data
public class Guarantee extends BaseDomain {

    //保修单编号【自动生成】
    private String sn = String.valueOf(System.currentTimeMillis());

    //结束日期【+1年】
    private Date endDate;

    //保修明细
    private List<GuaranteeItem> guaranteeItemList = new ArrayList<>();

    //所属合同
    private Contract contract;

}
