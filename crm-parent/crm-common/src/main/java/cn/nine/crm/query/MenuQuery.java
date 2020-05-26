package cn.nine.crm.query;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * (Menu)Query实体类
 *
 * @author 申林
 * @since 2020-05-24 11:53:35
 */
@Data
public class MenuQuery extends BaseQuery {
    private String time;

    public String getTime() {
        if (!StringUtils.isEmpty(time)) {/*时间处理特殊*/
            if (!",".equals(time)) {/*处理区间段是空，也会有个，号的*/
                StringBuilder sb = new StringBuilder();
                String start_time = time.split(",")[0];
                String end_time = time.split(",")[1];
                sb.append("and create_time > '" + start_time + "' and create_time < '" + end_time + "'");
                return sb.toString();
            }
        }
        return null;
    }
}
