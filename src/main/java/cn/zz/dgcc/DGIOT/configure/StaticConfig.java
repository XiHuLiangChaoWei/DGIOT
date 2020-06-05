package cn.zz.dgcc.DGIOT.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by: YYL
 * Date: 2020/6/4 16:34
 * ClassExplain :
 * ->
 */
@Configuration
public class StaticConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/app/**").addResourceLocations("file:d:/dgcc/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
