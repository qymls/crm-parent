//package cn.nine.crm.web.systemlog;
//
//import cn.nine.crm.domain.Employee;
//import cn.nine.crm.domain.Systemlog;
//import cn.nine.crm.service.ISystemlogService;
//import org.apache.shiro.SecurityUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Date;
//
//@Component
//@Aspect
//public class LogAop {
//    @Autowired
//    private HttpServletRequest request;
//
//    @Autowired
//    private ISystemlogService systemlogService;
//
//    private Date visitTime; //开始时间
//    private Class clazz; //访问的类
//    private Method method;//访问的方法
//    private StringBuffer params = new StringBuffer();
//
//    //前置通知  主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
//    @Before("execution(* cn.nine.crm.web.controller.*.*(..))")
//    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
//        visitTime = new Date();//当前时间就是开始访问的时间
//        clazz = jp.getTarget().getClass(); //具体要访问的类
//        System.out.println(clazz);
//        String methodName = jp.getSignature().getName(); //获取访问的方法的名称
//        Object[] args = jp.getArgs();//获取访问的方法的参数
//
//        //获取具体执行的方法的Method对象
//        if (args == null || args.length == 0) {
//            method = clazz.getMethod(methodName); //只能获取无参数的方法
//        } else {
//            Class[] classArgs = new Class[args.length];
//            for (int i = 0; i < args.length; i++) {
//                classArgs[i] = args[i].getClass();
//                params.append(args[i]);
//            }
//            clazz.getMethod(methodName, classArgs);
//        }
//    }
//
//    //后置通知
//    @After("execution(* cn.nine.crm.web.controller.*.*(..))")
//    public void doAfter(JoinPoint jp) throws Exception {
//        long time = new Date().getTime() - visitTime.getTime(); //获取访问的时长
//
//        String url = "";
//        //获取url
//        if (clazz != null && method != null && clazz != LogAop.class) {
//            System.out.println(clazz);
//            //1.获取类上的@RequestMapping("/orders")
//            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
//            if (classAnnotation != null) {
//                String[] classValue = classAnnotation.value();
//                //2.获取方法上的@RequestMapping(xxx)
//                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
//                if (methodAnnotation != null) {
//                    String[] methodValue = methodAnnotation.value();
//                    System.out.println(Arrays.toString(methodValue));
//                    url = classValue[0] + methodValue[0];
//
//                    //获取访问的ip
//                    String ip = request.getRemoteAddr();
//
//                    //获取当前操作的用户
//                    Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
//                    String username = employee.getUsername();
//
//                    //将日志相关信息封装到SysLog对象
//                    Systemlog systemlog = new Systemlog();
//                    systemlog.setOpip(ip);
//                    systemlog.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());
//                    systemlog.setRequesturi(url);
//                    systemlog.setOpuser(username);
//                    //systemlog.setParams(params.toString());
//
//                    //调用Service完成操作
//                    systemlogService.save(systemlog);
//                }
//            }
//        }
//
//    }
//}
