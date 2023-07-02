package org.yunqi.comon.config.mybatis;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * @Description swagger配置文件
 * @Author santong
 * @Since 2021/12/1 0:04
 **/
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("admin")
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .apiInfo(builderApiInfo())
                .select()
                // 扫描所有带有 @ApiOperation 注解的类
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描所有的 controller
                 .apis(RequestHandlerSelectors.basePackage("org.yunqi.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("app")
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .apiInfo(builderApiInfo())
                .select()
                // 扫描所有带有 @ApiOperation 注解的类
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描所有的 controller
                 .apis(RequestHandlerSelectors.basePackage("org.yunqi.app.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo builderApiInfo() {
        return new ApiInfoBuilder()
                .contact(
                        new Contact(
                                "云旗",
                                "",
                                "邮箱地址(2063604765@qq.com)"
                        )
                )
                .title("云旗crm项目")
                .description("云旗crm项目接口文档")
                .version("1.0")
                .build();
    }

    /**
     * 配置请求头 token
     */
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder()
                .securityReferences(Arrays.asList(SecurityReference.builder()
                        .reference("token")
                        .scopes(new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})
                        .build())).build());
    }

    /**
     * 配置请求头 token 参数
     */
    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new ApiKey("token凭证", "token", "header"));
    }

}

