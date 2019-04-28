package cn.huse.trace;

import cn.huse.trace.web.config.parsetoken.ParseTokenResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@SpringBootApplication
public class TraceApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(TraceApplication.class, args);
    }
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ParseTokenResolver());
    }
}
