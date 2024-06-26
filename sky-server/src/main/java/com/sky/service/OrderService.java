package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO 下单数据
     * @return 订单相关明细
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
