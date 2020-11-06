package com.pismo.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig implements WebMvcConfigurer {

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${spring.application.project.version}")
  private String appVersion;

  @Value("${api.title}")
  private String apiTitle;

  @Value("${api.description}")
  private String apiDescription;

    @Bean
    public Docket productApi() {

      final ApiInfo apiInfo = new ApiInfoBuilder()
              .title(apiTitle)
              .description(apiDescription)
              .version(appVersion)
              .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select().apis(RequestHandlerSelectors.basePackage("com.pismo.challenge"))
                .paths(PathSelectors.any())
                .build();
    }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/".concat(applicationName).concat("/v2/api-docs"), "/v2/api-docs").setKeepQueryParams(true);
    registry.addRedirectViewController("/".concat(applicationName).concat("/swagger-resources/configuration/ui"), "/swagger-resources/configuration/ui");
    registry.addRedirectViewController("/".concat(applicationName).concat("/swagger-resources/configuration/security"), "/swagger-resources/configuration/security");
    registry.addRedirectViewController("/".concat(applicationName).concat("/swagger-resources"), "/swagger-resources");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/".concat(applicationName).concat("/**")).addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}