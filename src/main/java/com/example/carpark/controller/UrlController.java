package com.example.carpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/url")
public class UrlController {

    /**
     * 路径跳转
     * @param path
     * @return
     */
    @RequestMapping("/{uri}")
    public String redirect(@PathVariable(value = "uri")String path){
        return "/administration/jsp/admin/"+path;
    }
}
