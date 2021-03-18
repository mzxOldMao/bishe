package mao.bishe.api.controller;

import mao.bishe.api.model.form.PageForm;
import mao.bishe.api.model.query.OrderBookId;
import mao.bishe.api.model.query.OrderBookQuery;
import mao.bishe.api.service.OrderService;
import mao.bishe.api.utils.PageListGer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/saveorder/{id}")
    public ResponseEntity setOrder(@PathVariable Long id, HttpServletRequest request){
        String studentId = request.getHeader("userName");
        Map<String,Object> map = new HashMap<>();
        if (studentId!=null){
            orderService.save(id,studentId);
            map.put("code",200);
            map.put("message","成功预订");
        }else {
            map.put("message","无法获取用户名");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/deleteorder/{id}")
    public ResponseEntity deleteOrder(@PathVariable String id, HttpServletRequest request){
        Long id1 = Long.parseLong(id);
        String studentId = request.getHeader("userName");
        Map<String,Object> map = new HashMap<>();
        if (studentId!=null){
            orderService.deleteOrder(id1,studentId);
            map.put("code",200);
            map.put("message","成功取消预订");
        }else {
            map.put("message","无法获取用户名");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getlist")
    public ResponseEntity getAllOrder(HttpServletRequest request){
        String userName = request.getHeader("userName");
        Map<String,Object> map = new HashMap<>();
        List<OrderBookId> list = orderService.findOrders1(userName);
        map.put("code",200);
        map.put("data",list);
        map.put("message","成功获取");
        return ResponseEntity.ok(map);
    }
    @GetMapping("/getorders")
    public ResponseEntity getAllOrders(HttpServletRequest request, PageForm pageForm){
        String userName = request.getHeader("userName");
        Map<String,Object> map = new HashMap<>();
        PageListGer<OrderBookQuery> orders = orderService.findOrders(userName, pageForm);
        map.put("code",200);
        map.put("data",orders);
        map.put("message","成功获取");
        return ResponseEntity.ok(map);
    }
}
