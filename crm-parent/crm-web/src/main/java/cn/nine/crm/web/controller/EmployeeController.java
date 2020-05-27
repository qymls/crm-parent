package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.EmployeeQuery;
import cn.nine.crm.util.Result;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Controller
@ResponseBody
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Employee,Long,EmployeeQuery> {
    private DefaultSecurityManager securityManager;/*shiro的核心对象*/

    @Autowired
    public void setSecurityManager(DefaultSecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Employee employee){
        SecurityUtils.setSecurityManager(securityManager);/*设置上下文*/

        Subject subject;
        try{
            //SecurityUtils :获取到当前的登录用户(主体)
            subject = SecurityUtils.getSubject();

            if(!subject.isAuthenticated()){
                //如果没登录
                UsernamePasswordToken token =
                        new UsernamePasswordToken(employee.getUsername(),employee.getPassword());
                //执行登录
                subject.login(token);
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return Result.error("用户名不存在!");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return Result.error("密码错误!");
        } catch (AuthenticationException e){
            e.printStackTrace();
            return Result.error("系统异常,吊打程序员！");
        }

        employee.setPassword("");
        Map<String,Object> result = new HashMap<>();
        result.put("user",employee);
        result.put("sessionId",subject.getSession().getId());

        return Result.ok(result,20000l);
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.singcode(20000l);
    }

}
