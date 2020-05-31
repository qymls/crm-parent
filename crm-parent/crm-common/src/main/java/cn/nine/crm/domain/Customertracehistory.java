package cn.nine.crm.domain;
import java.util.Date;
import lombok.Data;
/**
 * (Customertracehistory)实体类
 *
 * @author 申林
 * @since 2020-05-30 23:01:04
 */
@Data
public class Customertracehistory extends BaseDomain{
    /*客户*/
    private Customer customer;
    /*跟进人*/
    private Employee traceuser;
    /*跟进时间*/
    private Date tracetime;
    /*跟进方式*/
    private String tracetype;
    /*跟进效果*/
    private String traceresult;
    /*跟进主题*/
    private String title;
    /*备注*/
    private String remark;
    /*所属租户*/
    private Tenant tenant;



}
