package cn.huse.trace.web.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huanxi
 * @date: 2019/4/28 21:23
 */
@Configuration
public class BeenConfig {
    @Bean
    OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }
}
