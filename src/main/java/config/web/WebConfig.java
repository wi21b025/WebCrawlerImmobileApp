package config.web;

import config.web.util.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

    @Autowired
    private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/**")

                .excludePathPatterns(
                        "/resources/**",
                        "/static/**",
                        "/web-view/**",
                        "/modal/**",
                        "/index",
                        "/about"
                );
    }
}
