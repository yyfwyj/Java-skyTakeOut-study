package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        // 1. 根据begin与end推算出每天的日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }


        List<Double> turnoverList = new ArrayList<>();
        // 2. 获取每天的营业额
        for (LocalDate date : dateList) {
            // 营业额是指：订单状态为“已完成”的订单金额合计
            // 获取当天的开始时间与结束时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sunByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }


        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 1. 计算从开始时间到结束时间的所有日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        // 2. 计算所有的用户统计
        // 3. 计算新增的用户统计
        ArrayList<Integer> newUserList = new ArrayList<>();
        ArrayList<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("endTime", endTime);
            Integer totalUser = userMapper.countByMap(map);
            totalUserList.add(totalUser);
            map.put("beginTime", beginTime);
            Integer newUser = userMapper.countByMap(map);
            newUserList.add(newUser);
        }

        return UserReportVO.builder()
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .dateList(StringUtils.join(dateList, ","))
                .build();
    }

    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        // 1. 获取日期范围
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }


        Integer totalOrderCount = 0, validOrderCount = 0;
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);

            // 2. 查出所有订单以及汇总订单总数
            Integer oneDayTotalCount = orderMapper.reportByStatusAndOrderTime(map);
            oneDayTotalCount = oneDayTotalCount == null ? 0 : oneDayTotalCount;
            totalOrderCount += oneDayTotalCount;
            orderCountList.add(oneDayTotalCount);

            // 3. 查出有效订单以及汇总有效订单总数
            map.put("status", Orders.COMPLETED);
            Integer oneDayValidTotalCount = orderMapper.reportByStatusAndOrderTime(map);
            oneDayValidTotalCount = oneDayValidTotalCount == null ? 0 : oneDayValidTotalCount;
            validOrderCount += oneDayValidTotalCount;
            validOrderCountList.add(oneDayValidTotalCount);
        }
        Double orderCompletionRate = (double) (validOrderCount / totalOrderCount * 100);
        // 4. 计算订单完成率
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop(LocalDate begin, LocalDate end) {
        // 1. 获取时间范围
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 2. 传递时间，查询order_detail表的数据，以及order表的数据
        List<GoodsSalesDTO> salesTop = orderDetailMapper.getSalesTop(beginTime, endTime);

        // 解析 GoodsSalesDTO salesTop，构建 SalesTop10ReportVO。返回给前段
        List<String> nameList = salesTop.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = salesTop.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }
}
