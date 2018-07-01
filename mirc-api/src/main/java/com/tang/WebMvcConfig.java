package com.tang;

import com.tang.Interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态文件资源
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("file:D:/app/weixiApp/fileupload/");
    }

   @Bean
   public MiniInterceptor miniInterceptor() {
       return new MiniInterceptor();
   }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
                                                  .addPathPatterns("/video/uploadCover","/video/uploadVide","/video/userLike","/video/userUnLike")
                                                  .excludePathPatterns("/user/publisher")
                                                  .addPathPatterns("/bgm/**");
    }
    @Bean(initMethod = "init")
    public ZkCurratorCilent zkCurratorCilent() {
        return new ZkCurratorCilent();
    }
}
