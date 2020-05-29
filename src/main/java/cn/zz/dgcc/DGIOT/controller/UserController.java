package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.service.UserService;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/5/25 8:38
 * ClassExplain :
 * ->
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Autowired
    UserService userService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(success);
    }

    /**
     *          /users/login
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User ok = userService.Login(username, password);
        log.info("login name="+ok.getUserName());
        log.info("login id="+ok.getUserId());
        session.setAttribute("username", ok.getUserName());
        session.setAttribute("userId", ok.getUserId());
        session.setAttribute("userType",ok.getType());
        session.setAttribute("company",ok.getCompanyId());
        return new JsonResult<>(success,ok);

    }

    /**
     *        /user/change_password
     * @param session
     * @param password
     * @param exPwd
     * @return
     */
    @RequestMapping("change_password")
    public JsonResult<Void> changePwd(HttpSession session,
                                      @RequestParam(value="new_password",required = false)String password,
                                      @RequestParam(value="old_password",required = false)String exPwd){
        log.info(password+":"+exPwd);
        Integer userId = getUserIdFromSession(session);
        String modifiedUser = getUserNameFromSession(session);
        userService.changePwd(userId, password, exPwd);
        return new JsonResult<Void>(success);
    }


    @RequestMapping("get_by_id")
    public JsonResult<User> getById(HttpSession session){
        Integer userId = getUserIdFromSession(session);
        User ok =	userService.getByUid(userId);
        session.setAttribute("userId", ok.getUserId());
        return new JsonResult<>(success,ok);
    }

    @RequestMapping("logout")
    public JsonResult<Void> logout(HttpSession session){
        session.invalidate();
        return new JsonResult<>(success);
    }
}
