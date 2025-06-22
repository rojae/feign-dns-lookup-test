package kr.rojae.senderserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SenderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SenderServerApplication.class, args);
    }

}
