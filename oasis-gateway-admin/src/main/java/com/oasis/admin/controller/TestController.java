package com.oasis.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author zhushaobin
 * @Date 2022/4/19 11:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TestController {



    @PostMapping("/test")
    public String test(HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest);
        return "hello world";
    }
}
