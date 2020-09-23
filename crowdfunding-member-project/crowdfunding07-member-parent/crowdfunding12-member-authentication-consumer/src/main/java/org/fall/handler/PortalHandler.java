package org.fall.handler;

import org.fall.api.MySQLRemoteService;
import org.fall.constant.CrowdConstant;
import org.fall.entity.vo.PortalTypeVO;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PortalHandler {

    @Autowired
    MySQLRemoteService mySQLRemoteService;

    // 首页
    @RequestMapping("/")
    public String showPortalPage(ModelMap modelMap){

        // 调用MySQLRemoteService提供的方法查询首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        // 如果操作成功，将得到的list加入请求域
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<PortalTypeVO> portalTypeVOList = resultEntity.getData();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_TYPE_LIST,portalTypeVOList);
        }

        return "portal";
    }

}
