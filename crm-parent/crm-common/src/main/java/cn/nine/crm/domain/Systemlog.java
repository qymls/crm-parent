package cn.nine.crm.domain;
import java.util.Date;
import lombok.Data;
/**
 * (Systemlog)实体类
 *
 * @author 申林
 * @since 2020-05-29 10:11:04
 */
@Data
public class Systemlog extends BaseDomain{
    
    private String opuser;
    
    private String opip;
    
    private String requesturi;
    
    private String method;
    
    private String params;
    
    private String result;
    
    private Date optime;



}