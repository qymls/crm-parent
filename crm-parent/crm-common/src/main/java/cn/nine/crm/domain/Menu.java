package cn.nine.crm.domain;
import java.util.Date;
import lombok.Data;
/**
 * (Menu)实体类
 *
 * @author 申林
 * @since 2020-05-24 08:45:32
 */
@Data
public class Menu extends BaseDomain{
    
    private String name;
    
    private String url;
    
    private String icon;
    
    private Long parentId;
    
    private String label;
    
    private String englishName;
    
    private Date createTime;
    
    private String description;
    
    private String operator;
    
    private Long firstmenuid;



}