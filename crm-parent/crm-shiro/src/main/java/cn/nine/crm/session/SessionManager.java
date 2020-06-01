package cn.nine.crm.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *
 */
public class SessionManager  extends DefaultWebSessionManager {

    //请求头中的token: X-Token: 9CB58CFA7B5C7B91A521B237E116CA9B
    private static final String AUTHORIZATION = "X-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public SessionManager() {
        super();
    }

    //返回一个sessionId， 你这里返回什么，那么session管理器就会根据这个id去找对应的session对象
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //取到jessionid
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);  //X-Token
        //HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        //String id  httpServletRequest.getHeader(AUTHORIZATION);

        HttpServletRequest request1 = (HttpServletRequest) request;
        //如果请求头中有 X-TOKEN 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
