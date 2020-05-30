package cn.nine.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 保修单管理
 */
@Data
public class Guarantee extends BaseDomain {

    //保修单编号【自动生成】
    private String sn;

    //结束日期【+1年】
    private Date endDate;

    //保修明细
    private List<GuaranteeItem> guaranteeItemList = new ArrayList<>();

    //所属合同
    private Contract contract;

    public String getSn() {
        if (sn == null) {
            sn = String.valueOf(System.currentTimeMillis());
        }
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    public Date getEndDate() {
        return endDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
