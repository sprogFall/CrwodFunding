// 执行分页，生成分页效果
function generatePage(){
    var pageInfo = getPageInfoRemote();

    fillTableTBody(pageInfo);
}

// 从远程服务器端获取PageInfo数据
function getPageInfoRemote(){

    // 调用$.ajax()函数发送请求，并用ajaxResult接收函数返回值
    var ajaxResult = $.ajax({
        url:"role/page/page.json",
        type:"post",
        data:{
            "pageNum":window.pageNum,
            "pageSize":window.pageSize,
            "keyword":window.keyword
        },
        async:false,        //关闭异步模式，使用同步
        dataType:"json"
    });

    // 取得当前的响应状态码
    var statusCode = ajaxResult.status;

    // 判断当前状态码是不是200，不是200表示发生错误，通过layer提示错误消息
    if(statusCode != 200) {
        layer.msg("失败！状态码=" + statusCode + "错误信息=" + ajaxResult.statusText);
        return null;
    }

    // 响应状态码为200，进入下面的代码
    // 通过responseJSON取得handler中的返回值
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity取得result属性
    var result = resultEntity.result;

    // 判断result是否是FAILED
    if (result == "FAILED") {
        // 显示失败的信息
        layer.msg(resultEntity.message);
        return null;
    }

    // result不是失败时，获取pageInfo
    var pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;
}

// 根据PageInfo填充表格
function fillTableTBody(pageInfo){

    // 清除tbody中的旧内容
    $("#rolePageTBody").empty();

    // 使无查询结果时，不显示导航条
    $("#Pagination").empty();

    // 判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageTBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到想要的数据</td></tr>");
        return;
    }

    // pageInfo有效，使用pageInfo的list填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {

        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input type='checkbox' id='"+roleId+"' class='itemBox'/></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button type='button' id='"+roleId+"' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>"

        // 铅笔按钮用于修改role信息。用id属性（也可以是其他属性）携带当前的角色的id，class添加一个pencilBtn，方便添加响应函数
        var pencilBtn = "<button type='button' id='"+roleId+"' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>"

        var removeBtn = "<button type='button' id='"+roleId+"' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>"

        var buttonTd = "<td>"+checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageTBody").append(tr);
    }

    // 进行生成分页页码导航条
    generateNavigator(pageInfo);

}

// 生成分页页码导航条
function generateNavigator(pageInfo){


    //获取分页数据中的总记录数
    var totalRecord = pageInfo.total;

    //声明Pagination设置属性的JSON对象
    var properties = {
        num_edge_entries: 3,                                //边缘页数
        num_display_entries: 5,                             //主体页数
        callback: paginationCallback,                       //点击各种翻页反扭时触发的回调函数（执行翻页操作）
        current_page: (pageInfo.pageNum-1),                 //当前页码
        prev_text: "上一页",                                 //在对应上一页操作的按钮上的文本
        next_text: "下一页",                                 //在对应下一页操作的按钮上的文本
        items_per_page: pageInfo.pageSize   //每页显示的数量
    };

    $("#Pagination").pagination(totalRecord,properties);


}

// 翻页时的回调函数
function paginationCallback(pageIndex, jQuery){

    // pageIndex是当前页码的索引，因此比pageNum小1
    window.pageNum = pageIndex+1;

    // 重新执行分页代码
    generatePage();

    // 取消当前超链接的默认行为
    return false;

}



// 打开确认删除的模态框
function showConfirmModal(roleArray){
    // 显示模态框
    $("#confirmRoleModal").modal("show");

    // 清除旧的模态框中的数据
    $("#confirmList").empty();

    // 创建一个全局变量数组，用于存放要删除的roleId
    window.roleIdArray = [];

    // 填充数据
    for (var i = 0; i < roleArray.length; i++){

        var roleId = roleArray[i].id;

        // 将当前遍历到的roleId放入全局变量
        window.roleIdArray.push(roleId);

        var roleName = roleArray[i].name;

        $("#confirmList").append(roleName+"<br/>");
    }

}

// 生成权限信息的树形结构
function generateAuthTree(){

    var ajaxReturn = $.ajax({
        url: "assign/get/tree.json",
        type: "post",
        async: false,
        dataType: "json"
    });

    if (ajaxReturn.status != 200){
        layer.msg("请求出错！错误码："+ ajaxReturn.status + "错误信息：" + ajaxReturn.statusText);
        return ;
    }

    var resultEntity = ajaxReturn.responseJSON;

    if (resultEntity.result == "FAILED"){
        layer.msg("操作失败！"+resultEntity.message);
    }

    if (resultEntity.result == "SUCCESS"){
        var authList = resultEntity.data;
        // 将服务端查询到的list交给zTree自己组装
        var setting = {
            data: {
                // 开启简单JSON功能
                simpleData: {
                    enable: true,
                    // 通过pIdKey属性设置父节点的属性名，而不使用默认的pId
                    pIdKey: "categoryId"
                },
                key: {
                    // 设置在前端显示的节点名是查询到的title，而不是使用默认的name
                    name:"title"
                },
            },

            check: {
                enable:true
            }
        };

        // 生成树形结构信息
        $.fn.zTree.init($("#authTreeDemo"), setting, authList);

        // 设置节点默认是展开的
        // 1 得到zTreeObj
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
        // 2 设置默认展开
        zTreeObj.expandAll(true);

        // 勾选后端查出的匹配的权限
        ajaxReturn = $.ajax({
            url: "assign/get/checked/auth/id.json",
            type: "post",
            dataType: "json",
            async: false,
            data:{
                "roleId":window.roleId
            }
        });

        if (ajaxReturn.status != 200){
            layer.msg("请求出错！错误码："+ ajaxReturn.status + "错误信息：" + ajaxReturn.statusText);
            return ;
        }

        resultEntity = ajaxReturn.responseJSON;

        if (resultEntity.result == "FAILED"){
            layer.msg("操作失败！"+resultEntity.message);
        }

        if (resultEntity.result == "SUCCESS"){
            var authIdArray = resultEntity.data;

            // 遍历得到的autoId的数组
            // 根据authIdArray勾选对应的节点
            for (var i = 0; i < authIdArray.length; i++){
                var authId = authIdArray[i];

                // 通过id得到treeNode
                var treeNode = zTreeObj.getNodeByParam("id",authId);

                // checked设置为true，表示勾选节点
                var checked = true;

                // checkTypeFlag设置为false，表示不联动勾选，
                // 即父节点的子节点未完全勾选时不改变父节点的勾选状态
                var checkTypeFlag = false;

                // 执行勾选操作
                zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
            }
        }

    }
}