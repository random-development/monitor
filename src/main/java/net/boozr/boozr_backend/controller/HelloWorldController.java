package net.boozr.boozr_backend.controller;

import net.boozr.boozr_backend.exception.BadRequestApiException;
import net.boozr.boozr_backend.service.HelloWorldService;
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
