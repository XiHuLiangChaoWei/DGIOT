package cn.zz.dgcc.DGIOT.controller;


import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.service.UserService;
import cn.zz.dgcc.DGIOT.utils.Html.CaptchaUtils;
import cn.zz.dgcc.DGIOT.utils.Html.CodeTypeUtils;
import cn.zz.dgcc.DGIOT.utils.GenerateUUID;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;


/**
 * Created by: LT001
 * Date: 2019/10/23 15:36
 * ClassExplain :
 * ->
 */
@Controller
public class LoginController {
    private static final String 用户名或密码错误 = "00" ;
    private static final String 验证码错误 = "01" ;
    private static final String 登陆成功 = "02" ;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Resource
    private UserService userServiceImpl;


    @RequestMapping("/Login")
    public ModelAndView Login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("html/login");
        return mav;
    }


    @RequestMapping("/error/error2")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error2");
        return mav;
    }

    /**
     * 验证码生成
     */
    @RequestMapping("/Login/getCaptcha.do")
    public void captcha(String rnd, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("noUsed:" + rnd);
            //通过CaptchaUtils生成验证码图像，并存放到session
            BufferedImage image = CaptchaUtils.genCaptcha(request, response);
            response.setContentType("image/jpeg");
            //输出图像
            ServletOutputStream outStream = response.getOutputStream();
            ImageIO.write(image, "jpeg", outStream);
            outStream.close();
        } catch (Exception ex) {
            //logger.error(ex.getMessage(), ex);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/Login/loginCheck.do", method = RequestMethod.POST)
    public String loginCheck(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String captcha) {

        JSONObject json = new JSONObject();
        //空验证码
        if ("".equals(captcha) || null == captcha) {
            json.put("flag", 验证码错误);
            return json.toString();
        }
        //空用户名
        if ("".equals(username) || null == username) {
            json.put("flag", 用户名或密码错误);
            return json.toString();
        }
        //空密码
        if ("".equals(password) || null == password) {
            json.put("flag", 用户名或密码错误);
            return json.toString();
        }

        //获取request的cookie和session
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        String cookieId = "";
        //判断是服务器生成的验证码
        if (cookies != null) {
            //遍历cookies,取出cookies中 YsbCapcha 的值
            for (int i = 0; i < cookies.length; i++) {
                if ("YsbCaptcha".equals(cookies[i].getName())) {
                    cookieId = cookies[i].getValue();
                }
            }
        } else {
            json.put("flag", 验证码错误);
            return json.toString();
        }

        if ("".equals(cookieId)) {
            json.put("flag", 验证码错误);
            return json.toString();
        } else {
            //通过cookieId从获取session中,前端页面的验证码信息
            String attribute = (String) session.getAttribute(CodeTypeUtils.CAPTCHA + cookieId);
            if (!(captcha).toUpperCase().equals(attribute)) {
                json.put("flag", 验证码错误);
                return json.toString();
            }
        }


        User user = userServiceImpl.Login(username, password);
        log.info(user.toString());
        if (user == null) {
            json.put("flag", 用户名或密码错误);

        } else {

            String sessionId = GenerateUUID.GetUUID();
            Cookie cookie = new Cookie("SessionID", sessionId);
            cookie.setMaxAge(60 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);

            session.setAttribute("SessionID", sessionId);
            session.setAttribute("User", user);
            session.setAttribute("username", user.getUserName());
            session.setAttribute("userId", user.getUserId());
            session.setMaxInactiveInterval(60 * 30);
            json.put("flag", 登陆成功);
        }

        return json.toString();
    }


}
