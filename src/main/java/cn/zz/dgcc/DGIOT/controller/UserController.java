package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.service.UserService;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/5/25 8:38
 * ClassExplain :
 * ->
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Autowired
    UserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(success);
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || ip.indexOf(":") > -1) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = null;
            }
        }
        return ip;
    }

    /**
     * /users/login
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(HttpServletRequest h, String username, String password, HttpSession session) {
        User ok = userService.Login(username, password);
        log.info("login name:" + ok.getUserName()+"==login id:" + ok.getUserId()+"==ip:"+getClientIp(h));

        session.setAttribute("username", ok.getUserName());
        session.setAttribute("userId", ok.getUserId());
        session.setAttribute("userType", ok.getType());
        session.setAttribute("company", ok.getCompanyId());
        session.setAttribute("user",ok);
        session.setMaxInactiveInterval(60*60*12);

        return new JsonResult<>(success, ok);

    }

    /**
     * /user/change_password
     *
     * @param session
     * @param password
     * @param exPwd
     * @return
     */
    @RequestMapping("change_password")
    public JsonResult<Void> changePwd(HttpSession session,
                                      @RequestParam(value = "new_password", required = false) String password,
                                      @RequestParam(value = "old_password", required = false) String exPwd) {
        log.info(password + ":" + exPwd);
        Integer userId = getUserIdFromSession(session);
        String modifiedUser = getUserNameFromSession(session);
        userService.changePwd(userId, password, exPwd);
        userService.logout(getUserIdFromSession(session));
        return new JsonResult<Void>(success);
    }


    @RequestMapping("get_by_id")
    public JsonResult<User> getById(HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        User ok = userService.getByUid(userId);
        session.setAttribute("userId", ok.getUserId());
        return new JsonResult<>(success, ok);
    }

    @RequestMapping("logout")
    public JsonResult<Void> logout(HttpSession session) {
        System.err.println(session.getAttribute("username"));
        int rs = userService.logout(getUserIdFromSession(session));
        session.invalidate();
        return new JsonResult<>(success);
    }
}
