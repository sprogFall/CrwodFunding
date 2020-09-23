<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div id="confirmRoleModal" class="modal fade" tabindex="-1" role="dialog">
    <div  class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网-确认删除</h4>
            </div>

            <div class="modal-body">
                <h4>请确认是否删除下列角色！</h4>
                <%-- 确认的列表 --%>
                <div id="confirmList" style="text-align: center"></div>
            </div>
            <div class="modal-footer">
                <button id="confirmRoleBtn" type="button" class="btn btn-success">确认删除</button>
            </div>
        </div>
    </div>
</div>
