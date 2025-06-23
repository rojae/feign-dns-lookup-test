package kr.rojae.senderserver;

import kr.rojae.senderserver.config.FeignHttpClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "apiClient",
        url = "${api.receiver.host}:${api.receiver.port}",
        configuration = FeignHttpClientConfig.class
)
public interface ApiClient {

    @GetMapping("/")
    String index();

}
