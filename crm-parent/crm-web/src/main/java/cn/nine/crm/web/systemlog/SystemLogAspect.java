package cn.nine.crm.web.systemlog;


import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.Systemlog;
import cn.nine.crm.service.ISystemlogService;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.web.controller.SystemlogController;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private ISystemlogService systemLogService;


    //定义切点 @Pointcut
    //在注解的位置切入代码
    //@within 定义在类上，该类下的方法都被拦截
    //@annotation 写在方法上才能被拦截
    //方法和类上都支持
    @Pointcut("@within(cn.nine.crm.util.LogAnnotations) || @annotation(cn.nine.crm.util.LogAnnotations)")
    public void logPoinCut() {

    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        System.out.println("切面。。。。。来了");
        //保存日志
        Systemlog sysLog = new Systemlog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        LogAnnotations myLog = method.getAnnotation(LogAnnotations.class);
        if (myLog != null) {
            String value = myLog.value();
            System.out.println(value);
            //sysLog.setOpip(value);//保存获取的操作
        }

        //获取请求的url地址,两种方式
        String url = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();
        /*if (clazz != null && method != null && clazz != SystemLogAspect.class) {
            //1.获取类上的@RequestMapping("/orders")
           RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                //2.获取方法上的@RequestMapping(xxx)
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                  String[] methodValue = methodAnnotation.value();
                    System.out.println(Arrays.toString(methodValue));
                   url = "/"+classValue[0] +"/"+ methodValue[0];
                }}}*/
        sysLog.setRequesturi(url);
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);
        //操作时间
        sysLog.setOptime(new Date());
        //获取用户名
        //此例子用到了ShiroUtils框架来实现获取用户名，此处还可以用session来获取登录操作名
        //例：HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        sysLog.setOpuser(employee.getUsername());
        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        sysLog.setOpip(getIpAddress(request));

        sysLog.setResult("success");
        //调用service保存SysLog实体类到数据库
        Class clazz = joinPoint.getTarget().getClass();//具体要访问的类
        if (clazz != SystemlogController.class){/*日志管理不记录*/
            systemLogService.save(sysLog);
        }
    }
    /**
     *  发生异常，走此方法
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPoinCut()", throwing = "e")
    public void AfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("执行异常");
        try {
            Systemlog systemlog = new Systemlog();
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();

            systemlog.setMethod(className + "." + methodName);

            //请求的参数
            Object[] args = joinPoint.getArgs();
            //将参数所在的数组转换成json
            String params = JSON.toJSONString(args);
            systemlog.setParams(params);
            //操作时间
            systemlog.setOptime(new Date());
            // 用户id
            Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
            String username = employee.getUsername();
            //设置用户名
            systemlog.setOpuser(username);
            HttpServletRequest request = ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest();
            //设置ip
            systemlog.setOpip(getIpAddress(request));

            String url = ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();

            systemlog.setRequesturi(url);

            String exMsg = e.getCause().toString();

            systemlog.setResult("error: "+ exMsg);

            Class clazz = joinPoint.getTarget().getClass();//具体要访问的类
            if (clazz != SystemlogController.class){/*日志管理不记录*/
                systemLogService.save(systemlog);
            }
        } catch (Exception e1) {
           e1.printStackTrace();
        }
    }



    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
