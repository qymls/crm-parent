package cn.nine.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  //配置类，该类下面的配置相当于是在 application-context.yml中配置的效果一样
@EnableSwagger2 //开启Swagger配置
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                //这个是Swagger扫描的controller的基本包底层会把扫描到的类所在的包和这个基本包的路径进行前缀匹配
                .apis(RequestHandlerSelectors.basePackage("cn.qymls"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo(){
        @SuppressWarnings("deprecation")
        ApiInfo info=new ApiInfo(
                "CRM系统API文档",	//标题
                "客户端关系管理系统API文档说明",	//描述
                "1.0",	//版本
                "www.qymls.cn",	//服务条款网址
                "qymls",	//联系人
                "www.qymls.cn",//许可证
                "www.qymls.cn");   //许可证网址
        return info;
    }
}
