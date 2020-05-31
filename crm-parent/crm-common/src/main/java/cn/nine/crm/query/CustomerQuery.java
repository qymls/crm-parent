package cn.nine.crm.query;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 * (Customer)Query实体类
 *
 * @author 申林
 * @since 2020-05-29 22:15:24
 */
@Data
public class CustomerQuery extends BaseQuery{
    private String departmentName;

    private String jonName;

    private Long[] ids;

    private Boolean state;

}
