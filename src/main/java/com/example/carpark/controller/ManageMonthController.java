package com.example.carpark.controller;

import com.example.carpark.javabean.*;
import com.example.carpark.service.ChargeService;
import com.example.carpark.service.ManageMonthService;
import com.example.carpark.service.MonthService;
import com.example.carpark.util.MD5;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 收费员控制类
 */
@Controller
@RequestMapping("/month")
public class ManageMonthController {

    @Autowired
    private ManageMonthService manageMonthService;

    @Resource
    private PageBean pageBean;

    Gson g = new Gson();

    /**
     * 路径跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/path/{uri}")
    public String redirect(@PathVariable(value = "uri") String path) {
        return "/administration/jsp/admin/" + path;
    }

    //月缴参数表格显示
    @RequestMapping("/findMonthByPage")
    @ResponseBody
    public PageBean findMonthByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int page1 = Integer.valueOf(request.getParameter("page"));
        int limit = Integer.valueOf(request.getParameter("limit"));
        int page = (page1 - 1) * limit;

        HashMap<String, Object> condition = new HashMap<>();//condition是搜索条件和分页

        condition.put("page", page);
        condition.put("limit", limit);

        List<TbMonthChargeParameter> list = manageMonthService.findMonthByPage(condition);
        int count = manageMonthService.findMonthCount();

        if (list != null) {
            pageBean.setCode(0);
            pageBean.setCount(count);
            pageBean.setData(list);

            return pageBean;
        }

        return null;
    }

    //新增月缴参数信息
    @RequestMapping("/addMonth")
    public void addMonth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonstr = request.getParameter("tbMcp");
        TbMonthChargeParameter tbMcp = g.fromJson(jsonstr, TbMonthChargeParameter.class);
        System.out.println("新增月缴参数信息tbMcp= " + tbMcp.toString());
        int count = manageMonthService.addMonth(tbMcp);
        if (count > 0) {
            response.getWriter().print("success");
            System.out.println("addMonth新增成功");

        } else {
            response.getWriter().print("error");
            System.out.println("addMonth新增失败");
        }
    }

    //删除月缴参数信息
    @RequestMapping("/delMonthById")
    public void delMonthById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mcpId = request.getParameter("mcpId");
        System.out.println("删除月缴参数信息mcpId= " + mcpId);
        int count = manageMonthService.delMonthById(Integer.parseInt(mcpId));
        if (count > 0) {
            response.getWriter().print("success");
            System.out.println("delMonthById删除成功");

        } else {
            response.getWriter().print("error");
            System.out.println("delMonthById删除失败");
        }
    }

    //修改月缴参数信息
    @RequestMapping("/alterMonth")
    public void alterMonth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonstr = request.getParameter("tbMcp");
        TbMonthChargeParameter tbMcp = g.fromJson(jsonstr, TbMonthChargeParameter.class);
        System.out.println("修改月缴参数信息tbMcp= " + tbMcp.toString());
        int count = manageMonthService.alterMonth(tbMcp);
        if (count > 0) {
            response.getWriter().print("success");
            System.out.println("alterMonth修改成功");

        } else {
            response.getWriter().print("error");
            System.out.println("alterMonth修改失败");
        }
    }

}
