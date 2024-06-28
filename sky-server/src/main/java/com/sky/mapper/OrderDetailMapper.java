package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertBatch(List<OrderDetail> orderDetailList);

    List<GoodsSalesDTO> getSalesTop(LocalDateTime beginTime, LocalDateTime endTime);
}
