package kr.rojae.senderserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ApiController {

    private final ApiClient apiClient;

    public ApiController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("/")
    public String index() {
        log.info("===================");
        log.info("Api Send Request =>");
        String response = apiClient.index();
        log.info("Api Response => {}", response);
        log.info("===================");
        return response;
    }
}
