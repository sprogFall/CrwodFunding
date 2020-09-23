package org.fall.handler;

import org.fall.entity.vo.DetailProjectVO;
import org.fall.entity.vo.PortalTypeVO;
import org.fall.entity.vo.ProjectVO;
import org.fall.service.api.ProjectService;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectProviderHandler {

    @Autowired
    ProjectService projectService;


    // 将ProjectVO中的数据存入各个数据库
    @RequestMapping("/save/project/remote")
    public ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
        // 调用本地service进行保存
        try {
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }


    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote(){
        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVOList();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/detail/project/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId){
        return ResultEntity.successWithData(projectService.getDetailProjectVO(projectId));
    }

}
