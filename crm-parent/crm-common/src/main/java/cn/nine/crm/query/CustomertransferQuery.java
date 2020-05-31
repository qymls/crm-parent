package cn.nine.crm.query;

import lombok.Data;

import java.util.Date;

/**
 * (Customertransfer)Query实体类
 *
 * @author 申林
 * @since 2020-05-31 00:40:32
 */
@Data
public class CustomertransferQuery extends BaseQuery {
    private String departmentName;

    private Boolean state;


}
