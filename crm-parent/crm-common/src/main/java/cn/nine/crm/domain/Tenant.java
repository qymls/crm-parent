package cn.nine.crm.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Tenant extends BaseDomain {

    private String companyName;
    private String companyTel;
    private String companyNum;
    private String email;
    private String sysName;
    private Date registerTime;
    private String state;
    private String address;
    private String logo;
    private Meal meal;
    private boolean isPay;

}
