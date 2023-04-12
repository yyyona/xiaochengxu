package com.ordergoods.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {


    @Value("${com.jane.file.baseFilePath}")//从配置文件中获取属性值，并将其赋值给baseFilePath变量。
    private String baseFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+baseFilePath);//将"/file/**"路径映射到本地文件系统中的"baseFilePath"目录下
        WebMvcConfigurer.super.addResourceHandlers(registry);//将Spring Boot默认的静态资源处理器添加到已经自定义的静态资源处理器后面
    }

}

//静态资源的访问路径映射到相应的文件路径