package com.ordergoods.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.*;

@Configuration//springboot的配置文件
public class WebConfigurer implements WebMvcConfigurer {//接口

    @Value("${com.jane.file.baseFilePath}")
    private String baseFilePath;


    //跨域配置
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedOrigins("*"). //允许跨域的域名，可以用*表示允许任何域名使用
                        allowedMethods("*"). //允许任何方法（post、get等）
                        allowedHeaders("*"). //允许任何请求头
                        allowCredentials(true). //带上cookie信息
                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
                //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }

    // 配置静态资源，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+baseFilePath);//只要是file为前缀的，都会去后面地址里找文件
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        registry.addResourceHandler("/layuiadmin/**").addResourceLocations("classpath:/static/layuiadmin/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}


//配置访问Swagger UI：通过实现addViewControllers()方法，将"/swagger-ui/"路径映射到"/swagger-ui/index.html"上。
//配置静态资源：通过实现addResourceHandlers()方法，将静态资源的访问路径映射到相应的文件路径。
//注册拦截器：通过实现addInterceptors()方法，注册后台管理系统拦截器，并指定哪些请求需要拦截。
//其中@Value("${com.jane.file.baseFilePath}")注解用于从配置文件中获取属性值，并将其赋值给baseFilePath变量。
