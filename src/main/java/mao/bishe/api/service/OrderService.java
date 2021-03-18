package mao.bishe.api.service;

import mao.bishe.api.model.form.PageForm;
import mao.bishe.api.model.query.OrderBookId;
import mao.bishe.api.model.query.OrderBookQuery;
import mao.bishe.api.utils.PageListGer;

import java.util.List;

public interface OrderService {
    //新增预订订单
    void save(Long id, String studentId);
    //取消订单
    void deleteOrder(Long id, String studentId);
    //获取用户的所有订单
    PageListGer<OrderBookQuery> findOrders(String studentId, PageForm pageForm);
    List<OrderBookId> findOrders1(String studentId);
}
