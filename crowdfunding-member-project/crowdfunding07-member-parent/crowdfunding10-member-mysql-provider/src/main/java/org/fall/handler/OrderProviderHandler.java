package org.fall.handler;

import org.fall.entity.vo.AddressVO;
import org.fall.entity.vo.OrderProjectVO;
import org.fall.entity.vo.OrderVO;
import org.fall.service.api.OrderService;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderProviderHandler {

    @Autowired
    OrderService orderService;


    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVO(@RequestParam("returnId") Integer returnId) {

        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/address/list/by/member/id/remote")
    ResultEntity<List<AddressVO>> getAddressListByMemberIdRemote(@RequestParam("memberId") Integer memberId) {

        try {
            List<AddressVO> addressVOList = orderService.getAddressListVOByMemberId(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO) {
        try {
            orderService.saveAddressPO(addressVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO) {
        try {
            orderService.saveOrder(orderVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
