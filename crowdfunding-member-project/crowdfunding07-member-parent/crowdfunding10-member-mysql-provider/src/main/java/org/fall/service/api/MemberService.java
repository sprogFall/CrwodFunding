package org.fall.service.api;

import org.fall.entity.po.MemberPO;

public interface MemberService {

    MemberPO getMemberPOByLoginAcct(String loginAcct);

    void saveMember(MemberPO memberPO);
}
