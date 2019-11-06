package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class FontController {

    @RequestMapping("/error")
    public String error(){
        return "error";
    }
}
