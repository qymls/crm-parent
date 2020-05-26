package cn.nine.crm.domain;
import lombok.Data;
/**
 * (Permission)实体类
 *
 * @author 申林
 * @since 2020-05-26 15:09:36
 */
@Data
public class Permission extends BaseDomain{
    
    private String name;
    
    private String url;
    
    private String descs;
    
    private String sn;
    
    private Long menuId;



}