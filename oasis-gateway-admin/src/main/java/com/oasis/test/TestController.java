package com.oasis.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: za-zhushaobin
 * @Date: 2022/3/25 11:32
 * @Description:
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String test() {
        return "hello world";
    }
}
