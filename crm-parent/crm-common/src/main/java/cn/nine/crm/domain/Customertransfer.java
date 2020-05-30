package cn.nine.crm.domain;
import java.util.Date;
import lombok.Data;
/**
 * (Customertransfer)实体类
 *
 * @author 申林
 * @since 2020-05-31 00:40:32
 */
@Data
public class Customertransfer extends BaseDomain{

    /*顾客*/
    private Customer customer;
    /*移交操作人员*/
    private Employee transuser;
    /*移交时间*/
    private Date transtime;
    /*老市场专员*/
    private Employee oldseller;
    /*新市场专员*/
    private Employee newseller;
    /*移交原因*/
    private String transreason;
    /*所属租户*/
    private Tenant tenant;



}
