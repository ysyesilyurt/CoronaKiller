package com.coronakiller.Config;


import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
						.basePackage("com.coronakiller"))
				.paths(PathSelectors.regex("/.*"))
				.build().apiInfo(apiEndPointsInfo());
	}
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Corona Killer REST API")
				.description("CoronaKiller Game API Documentation")
				.contact(new Contact("Yavuz Selim Yesilyurt, Alper Kocaman", "user.ceng.metu.edu.tr/~e2259166", "yesilyurt.selim@metu.edu.tr"))
				.license("MIT")
				.licenseUrl("https://opensource.org/licenses/MIT")
				.version("1.0.0")
				.build();
	}
}