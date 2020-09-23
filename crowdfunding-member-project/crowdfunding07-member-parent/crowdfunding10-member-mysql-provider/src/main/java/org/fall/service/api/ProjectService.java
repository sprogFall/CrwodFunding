package org.fall.service.api;

import org.fall.entity.vo.DetailProjectVO;
import org.fall.entity.vo.PortalTypeVO;
import org.fall.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVOList();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
