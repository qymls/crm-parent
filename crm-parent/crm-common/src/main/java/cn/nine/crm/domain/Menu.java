package cn.nine.crm.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * (Menu)实体类
 *
 * @author 申林
 * @since 2020-05-24 08:45:32
 */
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", label='" + label + '\'' +
                ", englishName='" + englishName + '\'' +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                ", operator='" + operator + '\'' +
                ", firstmenuid=" + firstmenuid +
                ", rank=" + rank +
                ", children=" + children +
                '}';
    }

}
