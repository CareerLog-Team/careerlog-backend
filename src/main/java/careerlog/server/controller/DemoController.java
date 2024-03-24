package careerlog.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {
    @GetMapping("/demo")
    public String demo() {
        String message = "hello world";

        log.info(message);
        return message;
    }
}
