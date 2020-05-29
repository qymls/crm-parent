package cn.nine.crm.util;

import java.lang.annotation.*;

/**
 * 自定义注解类，用于日志管理
 */
@Target({ElementType.METHOD, ElementType.TYPE}) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Inherited
@Documented //生成文档
public @interface LogAnnotations {
    String value() default "";
}
