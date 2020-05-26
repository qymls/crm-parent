package cn.nine.crm.domain;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * (Role)实体类
 *
 * @author 申林
 * @since 2020-05-26 18:02:17
 */
@Data
public class Role extends BaseDomain{

    private String name;

    private String sn;

    private List<Permission> permissionList = new ArrayList<>();



}
