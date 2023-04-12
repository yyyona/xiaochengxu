package com.ordergoods.config.swagger;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 针对Swagger2
 */
//@Configuration
//@EnableSwagger2
public class Swagger2Configuration {

//    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ordergoods"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XXX")
                .description("XXX管理系统")
                .termsOfServiceUrl("http://localhost:8086/")
//                .contact("1274654983@qq.com")
                .version("1.0")
                .build();
    }
}
