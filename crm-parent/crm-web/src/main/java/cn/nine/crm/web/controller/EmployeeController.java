package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Employee;
import cn.nine.crm.query.EmployeeQuery;
import cn.nine.crm.util.LogAnnotations;
import cn.nine.crm.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@ResponseBody
@RequestMapping("/employee")
public class EmployeeController extends BaseController<Employee,Long,EmployeeQuery> {


}
