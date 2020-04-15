package com.example.carpark.controller;

import com.example.carpark.javabean.PageBean;
import com.example.carpark.javabean.TbChargerParameter;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.ChargeService;
import com.example.carpark.service.WhiteListService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 白名单管理
 */
@Controller
@RequestMapping("/white")
public class WhiteListController {

    @Autowired
    private WhiteListService whiteListService;

    //白名单查看
    @RequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        response.getWriter().write(whiteListService.queryWhiteList(page,limit));
        response.getWriter().flush();
    }
    //白名单修改
    @RequestMapping("/modifyWhiteList")
    public void modifyWhiteList(TbWhiteList tbWhiteList, HttpServletResponse response) throws IOException {
        int i = whiteListService.modifyWhiteList(tbWhiteList);
        if (i == 1) {
            response.getWriter().print("succeed");
        }
    }

    //白名单添加
    @RequestMapping("/addWhiteList")
    public void addWhiteList(TbWhiteList tbWhiteList, HttpServletResponse response) throws IOException {
        int i = whiteListService.addWhiteList(tbWhiteList);
        if (i == 1) {
            response.getWriter().print("succeed");
        }
    }

    //白名单删除
    @RequestMapping("/delWhiteList")
    public void delWhiteList(String data, HttpServletResponse response) throws IOException {
        TbWhiteList[] tbWhiteLists = new Gson().fromJson(data,TbWhiteList[].class);
        System.out.println("后台接受数据==="+new Gson().toJson(tbWhiteLists));
        Integer i = whiteListService.delWhiteList(tbWhiteLists);
        if (i == tbWhiteLists.length) {
            response.getWriter().print("succeed");
        }
    }
}
