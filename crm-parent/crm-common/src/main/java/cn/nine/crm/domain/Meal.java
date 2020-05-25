package cn.nine.crm.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Meal extends BaseDomain {
    private String name;
    private BigDecimal price;
    private Date expireDate;
    private boolean status;
}
