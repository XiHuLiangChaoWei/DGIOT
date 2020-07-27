package cn.zz.dgcc.DGIOT.listener;

import cn.zz.dgcc.DGIOT.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.websocket.Session;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/7/15 14:54
 * ClassExplain :
 */

@WebListener
public class SinglePointListener implements HttpSessionAttributeListener {
    public static final Map<String, HttpSession> map = new HashMap<>();
    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        User user = (User) event.getSession().getAttribute("user");
//        System.err.println("user"+user);
        if (user != null) {
            if (map != null) {
//                System.err.println("监听新建session获取到userName=" + user.getUserName());

                if (map.containsKey(user.getUserName())) {

                    log.warning("map中存在key={},取出sessionOld清空数据，并设置属性forcedout强制下线");
                    HttpSession sessionOld = map.get(user.getUserName());
                    if (sessionOld != null) {
                        Enumeration<?> e = sessionOld.getAttributeNames();
                        while (e.hasMoreElements()) {
                            String sessionKeyName = (String) e.nextElement();
                            sessionOld.removeAttribute(sessionKeyName);
                        }
                        sessionOld.setAttribute("forceLogOut", "yes");
                        map.put(user.getUserName(), event.getSession());
                    }
                } else {
                    System.err.println("map中不存在key");
                    map.put(user.getUserName(), event.getSession());
                }
//                Iterator<Map.Entry<String, HttpSession>> iter = map.entrySet().iterator();
//                //判断往下还有没有数据
//                while (iter.hasNext()) {
//                    //有的话取出下面的数据
//                    Map.Entry<String, HttpSession> entry = iter.next();
//                    String key = entry.getKey();
//                    HttpSession value = (HttpSession) entry.getValue();
//                    System.out.println(key + " ：" + value);
//                }
            }
        }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
