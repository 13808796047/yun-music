package com.summer.yunmusic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Summer
 * @since 2022/4/17 1:11
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    @GetMapping
    public String test(){
        return "test";
    }
}
