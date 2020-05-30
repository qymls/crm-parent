package cn.nine.crm.query;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * (Systemlog)Query实体类
 *
 * @author 申林
 * @since 2020-05-28 20:20:50
 */
@Data
public class SystemlogQuery extends BaseQuery {
    private String time;

    public String getTime() {
        if (!StringUtils.isEmpty(time)) {/*时间处理特殊*/
            if (!",".equals(time)) {/*处理区间段是空，也会有个，号的*/
                StringBuilder sb = new StringBuilder();
                String start_time = time.split(",")[0];
                String end_time = time.split(",")[1];
                sb.append("and optime > '" + start_time + "' and optime < '" + end_time + "'");
                return sb.toString();
            }
        }
        return null;
    }
}
