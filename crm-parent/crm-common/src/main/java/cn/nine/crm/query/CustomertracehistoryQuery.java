package cn.nine.crm.query;
import lombok.Data;
import java.util.Date;

/**
 * (Customertracehistory)Query实体类
 *
 * @author 申林
 * @since 2020-05-30 23:01:04
 */
@Data
public class CustomertracehistoryQuery extends BaseQuery{
    private String departmentName;

    private String status;


}
