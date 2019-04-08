package net.boozr.boozr_backend.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String getText() {
        return "Hello World!";
    }

}
