package kr.rojae.receiverserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ApiController {

    @GetMapping("/")
    public String get(){
        log.info("===================");
        log.info("Request received");
        log.info("===================");
        return "Hello World";
    }

}
