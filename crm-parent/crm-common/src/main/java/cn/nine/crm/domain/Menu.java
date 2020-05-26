package cn.nine.crm.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
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

    @JSONField(serialize = false)/*json转换时忽略*/
    private Menu parent;

    private String label;

    private String englishName;

    private Date createTime;

    private String description;

    private String operator;

    private Long firstmenuid;

    private Long rank;

    private List<Menu> children = new ArrayList<>();



}
