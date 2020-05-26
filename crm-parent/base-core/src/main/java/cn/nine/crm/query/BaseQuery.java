package cn.nine.crm.query;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class BaseQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private String keyword;

    public String getKeyword() {
        return StringUtils.isEmpty(keyword)?null:keyword.trim();
    }

    public Integer getStart() {
        return (this.currentPage - 1) * this.pageSize;
    }


}
