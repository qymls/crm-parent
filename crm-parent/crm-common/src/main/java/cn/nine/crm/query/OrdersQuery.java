package cn.nine.crm.query;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 定金订单 Query层
 *
 */
@Data
public class OrdersQuery extends BaseQuery{

    private String username;
    public String getUsername() {
        if (StringUtils.isNoneBlank(username)){
            return username;
        }
        return  null;
    }
}
