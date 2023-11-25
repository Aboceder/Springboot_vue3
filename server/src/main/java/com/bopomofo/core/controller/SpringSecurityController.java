package com.bopomofo.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityController {

    @GetMapping("/")
    public String hello() {
        return "hello security";
    }

    @GetMapping("/loginSuccessfully")
    public String loginSuccessfully() {
        return "redirect:https:www.google.com";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "redirect:https:www.baidu.com";
    }
}
