package com.example.carpark.controller;


import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.service.LoginService;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/index")
    public String index() {

        return "administration/jsp/index";
    }

    @RequestMapping("/BackMain")
    public String BackMain() {
        return "back/jsp/BackMain";
    }

    @RequestMapping("/path/{url}")
    public String url(@PathVariable("url") String url) {

        return "back/jsp/" + url;
    }

//    public ModelAndView index() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        System.out.println("打开页面");
//        return modelAndView;
//    }

    @RequestMapping("/login")
    public void Login(String acc, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("登录");
        String captcha = (String) request.getSession().getAttribute("vcode");
        Gson g = new Gson();
        TbAdmin tbadmin = g.fromJson(acc, TbAdmin.class);
//        if (captcha.equalsIgnoreCase(tbadmin.getCaptcha())) {

            TbAdmin admin1 = loginService.login(tbadmin.getAdminAcc(), tbadmin.getAdminPwd());

            System.out.println(g.toJson(admin1));
            if (null != admin1) {
                request.getSession().setAttribute("tbadmin", admin1);
                outWithHtml(response, "success");
                System.out.println("登录成功");
            } else {
                outWithHtml(response, "nameerror");
                System.out.println("帐号或密码错误");
            }
//        } else {
//            outWithHtml(response, "captchaerror");
//            System.out.println("验证码错误");
//        }
    }



    @RequestMapping("fileact")
    @ResponseBody
    public String uploadFile(@RequestParam("fileact") MultipartFile fileact, String test) {
        System.out.println(fileact.getOriginalFilename());
        System.out.println(test);

        try {
            fileact.transferTo(new File("E:/QQPCmgr/Desktop/" + fileact.getOriginalFilename()));
        } catch (Exception e) {

        }

        return "success";
    }

    @RequestMapping("/uploadTrainPicture")
    @ResponseBody
    public void addTrainPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        System.out.println(file.getOriginalFilename());

        try {
            file.transferTo(new File("E:/QQPCmgr/Desktop/" + file.getOriginalFilename()));
        } catch (Exception e) {

        }
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("{\"code\":0, \"msg\":\"\", \"data\":{}}");
        response.getWriter().flush();
        response.getWriter().close();

    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
        File file = new File("E:\\QQPCmgr\\Desktop\\传一\\框架ajax，静态资源，文件上传，转发重定向，乱码.mp4");
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    @RequestMapping("/download2")
    public ResponseEntity<byte[]> download2(HttpServletRequest request) throws IOException {
//        String path="D:\\test\\3阶段课堂笔记.txt";
        File file = new File("E:\\QQPCmgr\\Desktop\\传一\\框架ajax，静态资源，文件上传，转发重定向，乱码.mp4");
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("你好.mp4".getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    private Random random = new Random();

    @RequestMapping("/CheckCodeServlet")
    protected void CheckCodeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("验证码");
        //内存图片对象(TYPE_INT_BGR 选择图片模式RGB模式)
        BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_BGR);
        //得到画笔
        Graphics graphics = image.getGraphics();
        //画之前要设置颜色，设置画笔颜色
        graphics.setColor(new Color(236, 255, 253, 255));
        //设置字体类型、字体大小、字体样式　
        graphics.setFont(new Font("黑体", Font.BOLD, 23));
        //填充矩形区域（指定要画的区域设置区）
        graphics.fillRect(0, 0, 100, 35);
        //为了防止黑客软件通过扫描软件识别验证码。要在验证码图片上加干扰线
        //给两个点连一条线graphics.drawLine();
        for (int i = 0; i < 5; i++) {
            //颜色也要随机（设置每条线随机颜色）
            graphics.setColor(getRandomColor());
            int x1 = random.nextInt(100);
            int y1 = random.nextInt(35);
            int x2 = random.nextInt(100);
            int y2 = random.nextInt(35);
            graphics.drawLine(x1, y1, x2, y2);
        }

        //拼接4个验证码，画到图片上
        char[] arrays = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //设置字符的颜色

            int index = random.nextInt(arrays.length);
            builder.append(arrays[index]);
        }
        //创建session对象将生成的验证码字符串以名字为checkCode保存在session中request.getSession().setAttribute("checkCode",builder.toString());
        //将4个字符画到图片上graphics.drawString(str ,x,y);一个字符一个字符画
        request.getSession().setAttribute("vcode", builder.toString());
        for (int i = 0; i < builder.toString().length(); i++) {
            graphics.setColor(getRandomColor());
            char item = builder.toString().charAt(i);
            graphics.drawString(item + "", 10 + (i * 20), 25);
        }

        //输出内存图片到输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);

    }

    private void outWithHtml(HttpServletResponse response, String html) {

        System.out.println("返回值：" + html);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html,charset='UTF-8'");
            response.getWriter().write(html);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}