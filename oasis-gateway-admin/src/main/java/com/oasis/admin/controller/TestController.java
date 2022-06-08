package com.oasis.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author zhushaobin
 * @Date 2022/4/19 11:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class TestController {



    @PostMapping("/test")
    public String test(@RequestBody String test ){
        return test;
    }
}
