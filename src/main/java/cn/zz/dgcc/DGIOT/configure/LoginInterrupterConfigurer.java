package cn.zz.dgcc.DGIOT.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/25 9:30
 * ClassExplain :
 * ->
 */
@Configuration
public class LoginInterrupterConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterrupter();

        List<String> patterns = new ArrayList<>();
        patterns.add("/**/*.png");
        patterns.add("/**/*.bmp");
        patterns.add("/**/*.css");
        patterns.add("/**/*.js");
        patterns.add("/**/*.html");
        patterns.add("/**/*.ftl");
        patterns.add("/error/error2");
        patterns.add("/Login");
        patterns.add("/Login/getCaptcha.do");
        patterns.add("/Login/loginCheck.do");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }

}
