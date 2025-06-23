package kr.rojae.senderserver.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignHttpClientConfig {

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient(HttpClientBuilder.create().build());
    }
}