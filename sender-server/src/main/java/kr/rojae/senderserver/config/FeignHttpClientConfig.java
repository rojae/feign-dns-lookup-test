package kr.rojae.senderserver.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

@Configuration
public class FeignHttpClientConfig {

//    @Bean
//    public Client feignClient() {
//        return new ApacheHttpClient(HttpClientBuilder.create().build());
//    }

    @Bean
    public Client feignClient() {
        // NOT use connection pool
        HttpClientBuilder builder = HttpClientBuilder.create();

        // ConnectionReuseStrategy: Each Connection Close
        builder.setConnectionReuseStrategy((response, context) -> {
            int status = response.getStatusLine().getStatusCode();
            // status code is 200? keep-alive : connection close
            return status == 200;
        });

        // Request/Response Debugging
        builder.addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
            System.out.println(">> [Feign] Sending request to " + request.getRequestLine().getUri());
        });
        builder.addInterceptorFirst((HttpResponseInterceptor) (response, context) -> {
            System.out.println("<< [Feign] Response status: " + response.getStatusLine());
        });

        // Connection status debugging
        builder.setConnectionManagerShared(false);

        CloseableHttpClient httpClient = builder.build();
        return new ApacheHttpClient(httpClient);
    }
}