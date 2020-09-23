<%--
  Created by IntelliJ IDEA.
  User: Administrator_
  Date: 2020/8/12
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
    <%--
        base 标签必须写在 head 标签内部
        base 标签必须在所有“带具体路径”的标签的前面
        serverName 部分 EL 表达式和 serverPort 部分 EL 表达式之间必须写“:”
        serverPort 部分 EL 表达式和 contextPath 部分 EL 表达式之间绝对不能写“/”
        原因：contextPath 部分 EL 表达式本身就是“/”开头
        如果多写一个“/”会干扰 Cookie 的工作机制
        serverPort 部分 EL 表达式后面必须写“/”
    --%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

    <script src="jquery/jquery-2.1.1.min.js" type="text/javascript"></script>

    <script src="layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {

            //btn1
            //此方式可以在浏览器看到发送的请求体是Form Data(表单数据)
            $("#btn1").click(function () {
                $.ajax({
                    url: "send/array/one.html",
                    type: "post",
                    data: "array="+[5,8,12],
                    dataType: "text",
                    success: function (response) {
                        alert(response);
                    },
                    error: function (response) {
                        alert(response);
                    }

                });
            });

            //btn2
            //此方式可以在浏览器看到发送的请求体是Request Payload(请求负载)
            $("#btn2").click(function () {
                //准备要发送的数据
                var array=[5,8,12];
                //将JSON数组转换成JSON字符串
                var arrayStr = JSON.stringify(array);
                $.ajax({
                    url: "send/array/two.html",         //请求目标资源地址
                    type: "post",                       //请求方式
                    data: arrayStr,                     //发送的请求参数
                    dataType: "text",                   //表示如何对待服务器返回的数据
                    contentType: "application/json;charset=UTF-8",  //告诉服务器端当前请求的请求体是JSON格式
                    success: function (response) {
                        alert(response);
                    },
                    error: function (response) {
                        alert(response);
                    }

                });
            });

            //btn3
            //传输复杂对象
            $("#btn3").click(function () {
                var student = {
                    "name":"Fall",
                    "id":21,
                    "address":{
                        "province":"浙江",
                        "city":"宁波"
                    },
                    "subjects":[
                        {
                            "subjectName":"Java",
                            "score":96
                        },
                        {
                            "subjectName":"Data Struct",
                            "score":93
                        }
                    ],
                    "map":{
                        "key1":"value1",
                        "key2":"value2"
                    }
                };   //student end
                var studentStr = JSON.stringify(student);
                $.ajax({
                    url: "send/compose/object.html",         //请求目标资源地址
                    type: "post",                       //请求方式
                    data: studentStr,                     //发送的请求参数
                    dataType: "text",                   //表示如何对待服务器返回的数据
                    contentType: "application/json;charset=UTF-8",  //告诉服务器端当前请求的请求体是JSON格式
                    success: function (response) {
                        alert(response);
                    },
                    error: function (response) {
                        alert(response);
                    }
                });

            });     //btn3

            //btn4
            //使用ResultEntity，统一返回的格式
            $("#btn4").click(function () {
                var student = {
                    "name":"Fall",
                    "id":21,
                    "address":{
                        "province":"浙江",
                        "city":"宁波"
                    },
                    "subjects":[
                        {
                            "subjectName":"Java",
                            "score":96
                        },
                        {
                            "subjectName":"Data Struct",
                            "score":93
                        }
                    ],
                    "map":{
                        "key1":"value1",
                        "key2":"value2"
                    }
                };   //student end
                var studentStr = JSON.stringify(student);
                $.ajax({
                    url: "send/compose/object.json",         //请求目标资源地址
                    type: "post",                       //请求方式
                    data: studentStr,                     //发送的请求参数
                    dataType: "json",                   //表示如何对待服务器返回的数据
                    contentType: "application/json;charset=UTF-8",  //告诉服务器端当前请求的请求体是JSON格式
                    success: function (response) {
                        console.log(response);
                    },
                    error: function (response) {
                        console.log(response);
                    }
                });

            });//btn4

            //btn5
            $("#btn5").click(function () {
                // alert("nihao");
                layer.msg("1111");
            });


        });
    </script>
</head>
<body>
    <%--    无base标签：
        <a href="${pageContext.request.contextPath}/test/ssm.html">测试页面</a>
    --%>
    <%--  有base标签  --%>

    <a href="test/ssm.html">测试页面</a>
    <br/><br/>
    <button id="btn1">Test Ajax One</button>
    <br/><br/>
    <button id="btn2">Test Ajax Two</button>
    <br/><br/>
    <button id="btn3">Test Compose Object</button>
    <br/><br/>
    <button id="btn4">Test ResultEntity</button>

    <br><br>
    <a href="admin/login/page.html">登陆页面</a>

    <br/><br/>
    <button id="btn5">Test layer</button>

    <br><br>
    <br><br>
    http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/



</body>
</html>
