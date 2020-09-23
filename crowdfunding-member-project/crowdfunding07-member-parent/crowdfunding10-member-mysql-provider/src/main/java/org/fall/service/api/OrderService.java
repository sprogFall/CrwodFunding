package org.fall.service.api;

import org.fall.entity.vo.AddressVO;
import org.fall.entity.vo.OrderProjectVO;
import org.fall.entity.vo.OrderVO;

import java.util.List;

public interface OrderService {

    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressListVOByMemberId(Integer memberId);

    void saveAddressPO(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
