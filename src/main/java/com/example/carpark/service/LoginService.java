package com.example.carpark.service;


import com.example.carpark.aoplog.Log;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.util.MD5;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;



@Service
public class LoginService {

    @Resource
    private LoginDao loginDao;

    @Log(operationType = "登录操作", operationName = "用户登录")
    public TbAdmin login(String aacc, String apass) {
        System.out.println(aacc+apass);
        String md5 = MD5.machining(apass);

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("adminAcc", aacc);
//        hashMap.put("adminPwd", md5);

        TbAdmin tbadmin = loginDao.login(hashMap);

        Gson g = new Gson();
        System.out.println(g.toJson(tbadmin));
        return tbadmin;
    }


}
