package mao.bishe.api.config;

import mao.bishe.api.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/swagger-ui.html","/swagger-resources"
                        ,"/swagger-resources/configuration/ui","/swagger-resources/configuration/security"
                ,"/configuration/ui","/configuration/security","/v2/api-docs","/webjars/springfox-swagger-ui/**");
    }
}
