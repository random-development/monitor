package net.random_development.monitor.controller;

import net.random_development.monitor.exception.BadRequestApiException;
import net.random_development.monitor.service.HelloWorldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/world")
    public ResponseEntity<String> getWorld() {
        return ResponseEntity.ok(helloWorldService.getText());
    }

    @GetMapping("/error")
    public ResponseEntity<String> getError() {
        throw new BadRequestApiException();
    }

}
