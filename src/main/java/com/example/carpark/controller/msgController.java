package com.example.carpark.controller;

import com.example.carpark.javabean.*;
import com.example.carpark.service.ChargeService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/msg")
public class msgController {

    @Resource
    private ChargeService chargeService;

    /**
     * //     * 路径跳转
     * //     *
     * //     * @param path
     * //     * @return
     * //
     */
//    @RequestMapping("/path/{uri}")
//    public String redirect(@PathVariable(value = "uri") String path) {
//        return "demo/" + path;
//    }
    @RequestMapping("/getList")
    @ResponseBody
    public String getList(HttpServletRequest request) {
        Map<String, String> tbCashier = (Map<String, String>) request.getSession().getAttribute("msg");

        IMData imData = new IMData();

        //本人信息
        UserEntity userEntity = new UserEntity();
        userEntity.setStatus("online");
        userEntity.setUsername(tbCashier.get("name"));
        userEntity.setId(tbCashier.get("id"));
        if (tbCashier.get("sex").equals("男")) {
            userEntity.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
        } else {
            userEntity.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
        }
        imData.setMine(userEntity);

        Friend friend = new Friend();
        friend.setGroupname("收费员组");
        friend.setId(1);
        friend.setOnline(2);
        //收费员查询
        List<UserEntity> tbCashierUserEntities = new ArrayList<>();
        List<TbCashier> tbCashierList = chargeService.tbCashierQuery();
        for (int i = 0; i < tbCashierList.size(); i++) {
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId("" + tbCashierList.get(i).getCashierId());
            userEntity1.setUsername(tbCashierList.get(i).getCashierName());

            if (tbCashierList.get(i).getCashierSex().equals("男")) {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
            } else {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
            }
            tbCashierUserEntities.add(userEntity1);
        }
        friend.setList(tbCashierUserEntities);

        Friend friend1 = new Friend();
        friend1.setGroupname("管理员组");
        friend1.setId(2);
        friend1.setOnline(3);
        //管理员查询
        List<UserEntity> tbAdminUserEntities = new ArrayList<>();
        List<TbAdmin> tbAdminList = chargeService.tbAdminQuery();
        for (int i = 0; i < tbAdminList.size(); i++) {
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId("" + tbAdminList.get(i).getAdminId());
            userEntity1.setUsername(tbAdminList.get(i).getAdminName());

            if (tbAdminList.get(i).getAdminSex().equals("男")) {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
            } else {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
            }
            tbAdminUserEntities.add(userEntity1);
        }
        friend1.setList(tbAdminUserEntities);

        List<Friend> friendList = new ArrayList<>();
        friendList.add(friend);
        friendList.add(friend1);
        imData.setFriend(friendList);

        IMjson iMjson = new IMjson();
        iMjson.setData(imData);
        return new Gson().toJson(iMjson);
    }

    @RequestMapping("/getChargeId")
    @ResponseBody
    public String getChargeId(HttpServletRequest request) {
        Map<String, String> tbCashier = (Map<String, String>) request.getSession().getAttribute("msg");
        return tbCashier.get("id");
    }

    @RequestMapping("/getAdminId")
    @ResponseBody
    public String getAdminId(HttpServletRequest request) {
        TbAdmin tbAdmin = (TbAdmin) request.getSession().getAttribute("tbAdmin");
        return "" + tbAdmin.getAdminId();
    }

    @RequestMapping("/getAdminList")
    @ResponseBody
    public String getAdminList(HttpServletRequest request) {
        TbAdmin tbAdmin = (TbAdmin) request.getSession().getAttribute("tbAdmin");

        IMData imData = new IMData();

//        本人信息
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(tbAdmin.getAdminName());
        userEntity.setId(""+tbAdmin.getAdminId());
        if (tbAdmin.getAdminSex().equals("男")) {
            userEntity.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
        } else {
            userEntity.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
        }
        imData.setMine(userEntity);

        Friend friend = new Friend();
        friend.setGroupname("收费员组");
        friend.setId(1);
        friend.setOnline(2);
        //收费员查询
        List<UserEntity> tbCashierUserEntities = new ArrayList<>();
        List<TbCashier> tbCashierList = chargeService.tbCashierQuery();
        for (int i = 0; i < tbCashierList.size(); i++) {
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId("" + tbCashierList.get(i).getCashierId());
            userEntity1.setUsername(tbCashierList.get(i).getCashierName());

            if (tbCashierList.get(i).getCashierSex().equals("男")) {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
            } else {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
            }
            tbCashierUserEntities.add(userEntity1);
        }
        friend.setList(tbCashierUserEntities);

        Friend friend1 = new Friend();
        friend1.setGroupname("管理员组");
        friend1.setId(2);
        friend1.setOnline(3);
        //管理员查询
        List<UserEntity> tbAdminUserEntities = new ArrayList<>();
        List<TbAdmin> tbAdminList = chargeService.tbAdminQuery();
        for (int i = 0; i < tbAdminList.size(); i++) {
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setId("" + tbAdminList.get(i).getAdminId());
            userEntity1.setUsername(tbAdminList.get(i).getAdminName());

            if (tbAdminList.get(i).getAdminSex().equals("男")) {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/Hf7jkG82WKON9Ed.jpg");
            } else {
                userEntity1.setAvatar("https://i.loli.net/2020/04/26/nQEAZX1Y4GRSHyj.jpg");
            }
            tbAdminUserEntities.add(userEntity1);
        }
        friend1.setList(tbAdminUserEntities);

        List<Friend> friendList = new ArrayList<>();
        friendList.add(friend);
        friendList.add(friend1);
        imData.setFriend(friendList);

        return new Gson().toJson(imData);
    }
}
