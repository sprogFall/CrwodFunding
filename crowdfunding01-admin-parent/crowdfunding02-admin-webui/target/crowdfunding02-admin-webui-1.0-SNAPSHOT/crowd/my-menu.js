function myAddDiyDom(treeId, treeNode) {
    // treeId就是树形结构依附的ul的id
    // console.log("treeId="+treeId);
    // treeNode就是当前节点全部数据（包括后端查询得到的）
    // console.log(treeNode);

    // 根据zTree中每一个图标span的id的规则：
    // 如treeDemo_7_ico
    // id结构就是ul的id_当前节点序号_ico（tId就是id_当前节点序号）
    // 可以拼出每一个span的id：
    var spanId = treeNode.tId + "_ico";
    // 删除旧的class，增加新得到的class
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}

// 鼠标覆盖时，显示按钮组
function myAddHoverDom(treeId, treeNode) {
    // 定义增加、修改、删除节点的标签字符串
    var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='增加节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";

    // btn用于存放不同的节点显示的不同的按钮
    var btn = "";

    // 得到每个节点的level，根据level决定显示的按钮组的内容
    var level = treeNode.level;

    // 按照一定规则设置按钮组span的id
    var btnGroupId = "btnGroupTreeDemo_"+treeNode.id;

    // 如果此时按钮组已经有内容了，则不再往下执行
    if ($("#"+btnGroupId).length > 0){
        return ;
    }

    // 根据level决定按钮组内部显示的内容
    if (level === 0){
        btn = addBtn;
    } else if (level === 1){
        btn = addBtn + editBtn;
        // 判断是否子节点，有子节点则不显示删除按钮，没有子节点则显示删除按钮
        if (treeNode.children.length === 0){
            btn = btn + removeBtn;
        }
    } else {
        // level==3则显示删除按钮与修改按钮
        btn = editBtn+removeBtn;
    }

    // 拼接a标签的id（treeDemo_x_a）
    var aId = treeNode.tId + "_a";

    // 根据id，在a标签后加按钮组
    $("#"+aId).after("<span id='"+btnGroupId+"'>"+btn+"</span>");

}

// 鼠标移开时，隐藏按钮组
function myRemoveHoverDom(treeId, treeNode) {
    // 按钮组span的id
    var btnGroupId = "btnGroupTreeDemo_"+treeNode.id;
    // 删除此id的标签
    $("#"+btnGroupId).remove();
}

// 封装生成树形结构的代码
function generateTree(){
    $.ajax({
        url:"menu/do/get.json",
        type:"post",
        dataType:"json",
        success:function (response) {
            if (response.result == "SUCCESS"){
                var setting = {
                    view:{
                        "addDiyDom":myAddDiyDom,
                        "addHoverDom":myAddHoverDom,
                        "removeHoverDom":myRemoveHoverDom
                    },
                    data:{
                        key:{
                            // 实现“点了不跑”，也就是设置了这里的url后，会根据该url去寻找页面，如果页面找不到，则不跳转
                            /*
                            zTree 节点数据保存节点链接的目标 URL 的属性名称。
                            特殊用途：当后台数据只能生成 url 属性，又不想实现点击节点跳转的功能时，可以直接修改此属性为其他不存在的属性名称
                                默认值："url"
                            */
                            url: "NotExist"
                        }
                    }
                };
                var zNodes = response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (response.result == "FAILED")
                layer.msg("操作失败"+response.message)
        },
        error:function (response) {
            layer.msg("statusCode="+response.status + " message="+response.statusText);
        }
    });
}