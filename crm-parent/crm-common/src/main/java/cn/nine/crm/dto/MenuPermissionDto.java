package cn.nine.crm.dto;

import cn.nine.crm.domain.Menu;
import lombok.Data;

@Data
public class MenuPermissionDto {
    private Menu menu;

    private String[] permission;
}
