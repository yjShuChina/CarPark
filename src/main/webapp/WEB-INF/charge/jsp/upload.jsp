<%--
  Created by IntelliJ IDEA.
  User: junlong
  Date: 2019-11-16
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <%
        String path = request.getContextPath();
    %>
</head>
<body>
<input type="hidden" id="path" value="<%=path%>">
<form class="layui-form" action="" lay-filter="example">
    <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>新增文件</h2>
    </div>
    <div class="layui-inline" style="width:500px;">
        <hr>
    </div>
    <div class="layadmin-user-login-box layadmin-user-login-header">
        <div style="padding-bottom: 10px;">
            <div class="layui-upload">
                <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
            </div>
        </div>
        <div class="demoTable">
            <div style="padding-bottom: 10px;">
                <div class="layui-upload">
                    <button type="button" class="layui-btn" id="test9">保存</button>
                </div>
            </div>
        </div>
    </div>
<%--    <input type="text" value="1" name="test" id="test">--%>
</form>

<script>

    layui.use(['upload', 'jquery'], function () {
        var upload = layui.upload;
        var path = $("#path").val();
        //执行实例
        var uploadInst = upload.render({
            elem: '#test8' //绑定元素
            , url: path + '/charge/uploadTrainPicture' //上传接口
            , auto: false
            , accept: 'file'
            , bindAction: '#test9'
            , done: function (res) {

                layer.msg("上传成功！" + res);
            }
            , error: function () {
                //请求异常回调
                layer.msg("上传失败！");
            }


        });
    });

</script>
</body>
</html>
