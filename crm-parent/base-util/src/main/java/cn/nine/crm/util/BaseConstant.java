package cn.nine.crm.util;

/**
 * 常量类
 */

public class BaseConstant {
    /**
     * 0-在职
     * -1离职
     */
    //在职
    public static final Integer STATUS_WORK = 0;
    //离职
    public static final Integer STATUS_RESIGNATION = -1;

    /**
     * 员工类型
     * TYPE_NORMAL -普通机构员工
     * TYPE_CUSTOMER-平台客服
     * TYPE_TENANT_ADMIN -租户管理员
     * TYPE_PLAT_ADMIN-平台管理员
     *
     */
    public static final Integer TYPE_NORMAL = 0;
    public static final Integer TYPE_CUSTOMER = 1;
    public static final Integer TYPE_PLAT_ADMIN = 2;
    public static final Integer TYPE_TENANT_ADMIN = 3;
}
