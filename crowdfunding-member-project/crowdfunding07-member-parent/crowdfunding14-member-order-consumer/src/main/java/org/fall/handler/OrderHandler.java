package org.fall.handler;

import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.fall.api.MySQLRemoteService;
import org.fall.constant.CrowdConstant;
import org.fall.entity.vo.AddressVO;
import org.fall.entity.vo.LoginMemberVO;
import org.fall.entity.vo.OrderProjectVO;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderHandler {

    @Autowired
    MySQLRemoteService mySQLRemoteService;

    // 进入确认回报信息的页面
    @RequestMapping("/confirm/return/info/{returnId}")
    public String confirmReturnInfo(
            @PathVariable("returnId") Integer returnId,
            HttpSession session) {

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVO(returnId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            session.setAttribute("orderProjectVO", resultEntity.getData());
        }

        return "order-confirm-return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String toConfirmOrderPage(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session ) {
        // 从session域拿到orderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        // 给orderProjectVO设置回报的数量
        orderProjectVO.setReturnCount(returnCount);

        // 重新放回session域
        session.setAttribute("orderProjectVO",orderProjectVO);

        // 获取当前的用户的Address对象（即收货地址信息）

        // 1、先得到当前的用户id
        LoginMemberVO loginMember = (LoginMemberVO)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();

        // 2、通过用户id得到他的收货地址的List
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressListByMemberIdRemote(memberId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            // 3、如果成功，则将收货地址放入session域
            List<AddressVO> addressVOList = resultEntity.getData();
            session.setAttribute("addressVOList", addressVOList);
        }

        // 进入确认订单页面
        return "order-confirm-order";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session) {

        // 通过远程方法保存地址信息
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        // 从session域得到当前的orderProjectVO
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        // 得到当前的回报数量
        Integer returnCount = orderProjectVO.getReturnCount();

        // 再次重定向到确认订单的页面（附带回报数量）
        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }



}
