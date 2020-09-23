package org.fall.api;

import org.fall.entity.po.MemberPO;
import org.fall.entity.vo.*;
import org.fall.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("crowd-mysql")
public interface MySQLRemoteService {


    @RequestMapping("/get/member/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/remote")
    ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote();

    @RequestMapping("/get/detail/project/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId);

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVO(@RequestParam("returnId") Integer returnId);

    @RequestMapping("/get/address/list/by/member/id/remote")
    ResultEntity<List<AddressVO>> getAddressListByMemberIdRemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
}
