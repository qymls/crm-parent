package cn.nine.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Spring的配置标签，打了这个标签的类就是Spring的配置类，
// 该类下面的配置相当于是在 application-context.xml中配置的效果一样
@Configuration
@EnableSwagger2 //开启Swagger配置
public class Swagger2 {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                //这个是Swagger扫描的controller的基本包底层会把扫描到的类所在的包和这个基本包的路径进行前缀匹配
                .apis(RequestHandlerSelectors.basePackage("cn.rex"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo(){
        @SuppressWarnings("deprecation")
        ApiInfo info=new ApiInfo(
                "CRM系统API文档",	//标题
                "客户端关系管理系统API文档说明",	//描述
                "1.0",	//版本
                "www.itsource.cn",	//服务条款网址
                "whale.cen",	//联系人
                "www.itsource.cn",//许可证
                "www.itsource.cn");   //许可证网址
        return info;
    }
}