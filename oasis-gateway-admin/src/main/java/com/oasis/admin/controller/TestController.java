package com.oasis.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author zhushaobin
 * @Date 2022/4/19 11:10
 */
@RestController
@RequestMapping("/auth/")
public class TestController {

    @GetMapping("test")
    public String test(){
        return "hello world";
    }
}
