package cn.nine.crm.dto;

import lombok.Data;

import java.util.List;

//接收批量删除多个ID
@Data
public class IdsDto {
    private List<Long> ids;
}
