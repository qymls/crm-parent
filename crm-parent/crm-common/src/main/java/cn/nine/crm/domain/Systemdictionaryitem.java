package cn.nine.crm.domain;
import lombok.Data;
/**
 * (Systemdictionaryitem)实体类
 *
 * @author ReX
 * @since 2020-05-29 14:28:29
 */
@Data
public class Systemdictionaryitem extends BaseDomain{
    
    private Long parentId;
    
    private String name;
    
    private Integer requence;
    
    private String intro;



}