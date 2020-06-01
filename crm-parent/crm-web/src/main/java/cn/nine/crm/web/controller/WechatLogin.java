package cn.nine.crm.web.controller;


import cn.nine.crm.domain.Employee;
import cn.nine.crm.domain.WeChatUser;
import cn.nine.crm.dto.EmployeeLoginDto;
import cn.nine.crm.service.IEmployeeService;
import cn.nine.crm.service.IWeChatUserService;
import cn.nine.crm.token.WeChatToken;
import cn.nine.crm.util.HttpClientUtils;
import cn.nine.crm.util.Result;
import cn.nine.crm.util.WeChatLongConstant;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wy
 * @date 2020/5/30
 */
@Controller
@RequestMapping("/wechat")
public class WechatLogin {

    private IWeChatUserService weChatUserService;
    private IEmployeeService employeeService;


    public WechatLogin(IWeChatUserService weChatUserService, IEmployeeService employeeService) {
        this.weChatUserService = weChatUserService;
        this.employeeService = employeeService;
    }

    @GetMapping("/QRCode")
    @ResponseBody
    public Result getQRCode(){
        //1.发送请求获取二维码
        String QRCodeUrl = String.format(WeChatLongConstant.CODEURL,
                WeChatLongConstant.APPID,
                WeChatLongConstant.CALLBACK,
                WeChatLongConstant.SCOPE,
                WeChatLongConstant.STATE);
        System.out.println(QRCodeUrl);
        //2.重定向，让浏览器显示二维码
        //return "redirect:"+QRCodeUrl;无法重重定向,返回前端跳转
        return Result.ok(QRCodeUrl);

    }

    @GetMapping("/QRCodeCallBack")
    public String QRCode(String code, String state) throws Exception {
        System.out.println("回调函数成功");
        if(!StringUtils.hasLength(code) || !StringUtils.hasLength(state) || !state.equals(WeChatLongConstant.STATE) ){
            return "无效的授权码";
        }
        //2.根据授权码换取token
        String tokenResultString = HttpClientUtils.httpPost(WeChatLongConstant.ACCESSTOKEN_BASEURL,
                String.format(
                        WeChatLongConstant.ACCESSTOKE_PARAMS,
                        WeChatLongConstant.APPID,
                        WeChatLongConstant.APPSECRET,code));

        Map<String,String> tokenResultMap = JSON.parseObject(tokenResultString, Map.class);

        String token = tokenResultMap.get("access_token");

        String openId = tokenResultMap.get("openid");

        //System.out.println(tokenResultMap);

        //3.如果微信还没有绑定了平台的账户
        WeChatUser weChatUser = weChatUserService.selectByOpenId(openId);
        if(weChatUser == null){
            //3.1.根据token获取用户信息
            String access_token = tokenResultMap.get("access_token");
            System.out.println(access_token);
            String userInfoResultString = HttpClientUtils.httpPost(WeChatLongConstant.USERINFO_BASEURL, String.format(WeChatLongConstant.USERINFO_PARAMS, token, openId));
            weChatUser = JSON.parseObject(userInfoResultString,WeChatUser.class);
            weChatUserService.insert(weChatUser);

            //3.3.重定向到平台账户绑定页面
            //model.addAttribute("openid",openId);
            //重定向到前端页面
            return "redirect:http://localhost:9527/#/bind?openId="+openId;
        }
        Subject subject = SecurityUtils.getSubject();
        Employee employee = employeeService.findOne(weChatUser.getEmpId());
        if(!subject.isAuthenticated()){
            WeChatToken Token = new WeChatToken(employee.getUsername());
            subject.login(Token);
        }
        if(subject.isAuthenticated()){
            //跳转前段处理登录成功页面
            String user = URLEncoder.encode(JSON.toJSONString(subject.getPrincipal()), "UTF-8");
            String params ="openId="+openId+"&sessionId="+subject.getSession().getId()+"&user="+user;
            return "redirect:http://localhost:9527/#/success?"+params;
        }

        String errorMessage = URLEncoder.encode("无效的绑定","utf-8");
        return "redirect:http://localhost:8080/BindSuccess?errorMessage="+errorMessage;
    }

    @PostMapping("/bind")
    @ResponseBody
    public Result bindWeChatUser(@RequestBody EmployeeLoginDto employeeLoginDto){
        Subject subject = null;
        try{
            //1.绑定用户需要提交用户名和密码，所以先登录
            subject = SecurityUtils.getSubject();
            if(!subject.isAuthenticated()){
                UsernamePasswordToken token = new UsernamePasswordToken(employeeLoginDto.getUsername(),employeeLoginDto.getPassword());
                subject.login(token);
            }

            if(subject.isAuthenticated()){
                //如果登录成功，就绑定openId到用户
                //1.根据用户名查询用户
                Employee employee = employeeService.findByUsername(employeeLoginDto.getUsername());

                //2.把用户和微信对象关联
                WeChatUser weChatUser = weChatUserService.selectByOpenId(employeeLoginDto.getOpenId());
                weChatUser.setEmpId(employee.getId());
                weChatUserService.update(weChatUser);

                Map<String,Object> result = new HashMap<>();
                result.put("sessionId",subject.getSession().getId());
                result.put("user",employee);
                return Result.ok(result);
            }

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return Result.error("用户名不存在!");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return Result.error("密码错误!");
        } catch (AuthenticationException e){
            e.printStackTrace();
            return Result.error("系统异常！");
        }
        return Result.error("绑定失败!");
    }
}
