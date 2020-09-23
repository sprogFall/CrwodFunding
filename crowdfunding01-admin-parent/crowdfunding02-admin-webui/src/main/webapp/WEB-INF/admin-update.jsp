<%--
  Created by IntelliJ IDEA.
  User: Administrator_
  Date: 2020/8/17
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<body>

<%@include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/main/page.html">首页</a></li>
                <li><a href="admin/page/page.html">数据列表</a></li>
                <li class="active">更新</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <form action="admin/page/doUpdate.html" method="post" role="form">
                        <p>${requestScope.exception.message}</p>
                        <%-- type=hidden，因为这些数据不需要（pageNum、keyword）或不应该被修改（id、createTime） --%>
                        <input type="hidden" name="id" value="${requestScope.admin.id}"/>
                        <input type="hidden" name="createTime" value="${requestScope.admin.createTime}"/>
                        <input type="hidden" name="pageNum" value="${requestScope.pageNum}"/>
                        <input type="hidden" name="keyword" value="${requestScope.keyword}"/>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <%-- 通过value给各个文本框赋原始值 --%>
                            <input type="text" name="loginAcct" class="form-control" id="exampleInputPassword1" value="${requestScope.admin.loginAcct}" placeholder="请输入登录账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" name="userName" class="form-control" id="exampleInputPassword1" value="${requestScope.admin.userName}" placeholder="请输入用户昵称">
                        </div>

                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" name="email" class="form-control" id="exampleInputEmail1" value="${requestScope.admin.email}" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus">修改</i> </button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh">重置</i> </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
