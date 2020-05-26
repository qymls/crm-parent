package cn.nine.crm.dto;

import lombok.Data;

import java.util.List;

@Data
public class IdsDto {

    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
