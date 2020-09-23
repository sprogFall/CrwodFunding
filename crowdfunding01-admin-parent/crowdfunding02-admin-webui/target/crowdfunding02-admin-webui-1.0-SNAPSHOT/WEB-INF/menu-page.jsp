<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script>
    $(function () {
        // 将原本放在页面中的js代码封装到了特定了generateTree()函数中
        generateTree();

        // 给“+”按钮，添加单击响应函数，打开添加节点的模态框
        $("#treeDemo").on("click",".addBtn",function () {

            window.pid = this.id;

            $("#menuAddModal").modal("show");


            return false;
        });

        // 添加节点模态框中保存按钮的单击事件
        $("#menuSaveBtn").click(function () {
            var name = $.trim($("#menuAddModal [name=name]").val());

            var url = $.trim($("#menuAddModal [name=url]").val());

            var icon = $("#menuAddModal [name=icon]:checked").val();

            $.ajax({
                url:"menu/save.json",
                type:"post",
                "data":{
                    "name":name,
                    "url":url,
                    "icon":icon,
                    "pid":window.pid
                },
                dataType:"json",
                success:function (response) {
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");

                        // 重新生成树形结构
                        generateTree();

                    }
                    if (response.result == "FAILED"){
                        layer.msg("操作失败！");
                    }
                },
                error:function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }

            });

            // 关闭模态框
            $("#menuAddModal").modal("hide");

            // 清空模态框内的数据(通过模拟用户单击“重置”按钮)
            $("#menuResetBtn").click();

        });

        // 动态生成的修改按钮，单击打开修改的模态框
        $("#treeDemo").on("click",".editBtn",function () {

            // 保存此按钮的id
            window.id = this.id;

            $("#menuEditModal").modal("show");

            // 要实现通过id拿到整个节点的信息，需要拿到zTreeObj
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            var key = "id";
            var value = window.id;

            // getNodeByParam，通过id得到当前的整个节点
            var currentNode = zTreeObj.getNodeByParam(key,value);

            $("#menuEditModal [name=name]").val(currentNode.name);

            $("#menuEditModal [name=url]").val(currentNode.url);
            // 这里currentNode.icon其实是数组形式，利用这个值，放在[]中，传回val，就可以使相匹配的值回显在模态框中
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });


        // 修改的模态框”修改按钮“的单击事件
        $("#menuEditBtn").click(function () {
            var name = $.trim($("#menuEditModal [name=name]").val());

            var url = $.trim($("#menuEditModal [name=url]").val());

            var icon = $("#menuEditModal [name=icon]:checked").val();

            $.ajax({
                url:"menu/edit.json",
                type:"post",
                "data":{
                    "id":window.id,
                    "name":name,
                    "url":url,
                    "icon":icon
                },
                dataType:"json",
                success:function (response) {
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");

                        // 重新生成树形结构
                        generateTree();

                    }
                    if (response.result == "FAILED"){
                        layer.msg("操作失败！");
                    }
                },
                error:function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }

            });

            // 关闭模态框
            $("#menuEditModal").modal("hide");

        });

        // 动态生成的删除按钮的单击事件，（打开确认模态框）
        $("#treeDemo").on("click",".removeBtn",function () {
            window.id = this.id;
            // 打开模态框
            $("#menuConfirmModal").modal("show");

            // 进行回显
            // 拿到zTreeObject
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            var key = "id";
            var value = window.id;

            // 通过getNodeByParam()，传入当前节点的id，得到当前节点的对象
            var currentNode = zTreeObj.getNodeByParam(key, value);

            // 通过得到的节点对象得到该节点的name与icon
            var name = currentNode.name;
            var icon = currentNode.icon;

            // 向id=removeNodeSpan的span标签添加html语句
            $("#removeNodeSpan").html("【<i class='"+icon+"'>"+name+"】</i>");

            // 关闭默认跳转
            return false;

        });

        // 确认模态框中，“确认”按钮的单击事件（发送Ajax请求）
        $("#confirmBtn").click(function () {
            $.ajax({
                url:"menu/remove.json",
                type:"post",
                "data":{
                    "id":window.id,
                },
                dataType:"json",
                success:function (response) {
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");

                        // 重新生成树形结构
                        generateTree();

                    }
                    if (response.result == "FAILED"){
                        layer.msg("操作失败！");
                    }
                },
                error:function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            $("#menuConfirmModal").modal("hide");
        });

    });
</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">
                        <%-- 显示树形结构依附于上面的ul --%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
</body>
</html>
