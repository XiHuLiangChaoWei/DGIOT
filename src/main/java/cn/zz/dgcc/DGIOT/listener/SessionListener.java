package cn.zz.dgcc.DGIOT.listener;

import cn.zz.dgcc.DGIOT.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/7/15 14:41
 * ClassExplain : 当session创建时，判断用户状态
 * ->
 */

@WebListener
public class SessionListener implements HttpSessionListener {
    Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.warning("Session{}创建"+se.getSession().getId());
//        System.err.println("Session{}创建"+se.getSession().getId());
        se.getSession().setAttribute("forceLogOut","no");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession().getAttribute("user");
//        System.err.println(user);
        if(user!=null) SinglePointListener.map.remove(user.getUserName());
//        log.warning("Session{}销毁"+se.getSession().getId());
    }
}
