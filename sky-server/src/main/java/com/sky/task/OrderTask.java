package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每分钟查询一次，是否有未支付的订单，同时该订单的下单时间超过了15分钟
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟一次
    public void processTimeoutOrder() {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 每天凌晨一点查询一次，是否有派送中的订单，将订单状态更改为已完成
     */
    @Scheduled(cron = "* * 1 * * ?")
    public void processDeliveryOrder() {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
