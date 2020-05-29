package cn.zz.dgcc.DGIOT.configure;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by: YYL
 * Date: 2020/5/25 9:25
 * ClassExplain :
 * ->
 */
public class LoginInterrupter implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(request.getSession().getAttribute("userId")==null) {
            response.sendRedirect(request.getContextPath()+"/Login");
            return false;
        }
        return true;

    }

}
