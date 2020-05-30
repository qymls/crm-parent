package cn.nine.crm.domain;
import lombok.Data;
/**
 * (Systemdictionary)实体类
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
@Data
public class Systemdictionary extends BaseDomain{
    
    private String sn;
    
    private String name;
    
    private String intro;
    
    private boolean state;
    
    private Systemdictionaryitem item;
    
    private Tenant tenant;



}