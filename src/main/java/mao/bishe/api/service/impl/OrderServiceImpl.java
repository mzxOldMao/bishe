package mao.bishe.api.service.impl;

import mao.bishe.api.dao.OrderDao;
import mao.bishe.api.model.Order;
import mao.bishe.api.model.form.PageForm;
import mao.bishe.api.model.query.OrderBookId;
import mao.bishe.api.model.query.OrderBookQuery;
import mao.bishe.api.service.OrderService;
import mao.bishe.api.utils.PageListGer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(Long id, String studentId) {
        String classsId = studentId.substring(0,7);
        Order order = new Order();
        order.setBookId(id);
        order.setFlag(1);
        //order.setCreateTime(new Date());
        order.setStudentId(studentId);
        order.setClassId(classsId);
        System.out.println(order);
        orderDao.save(order);

    }

    @Override
    public void deleteOrder(Long id, String studentId) {
        String sql = "delete from orders where student_id = '"+studentId+"' and book_id = "+id+";";
        int update = jdbcTemplate.update(sql);
    }

    @Override
    public PageListGer<OrderBookQuery> findOrders(String studentId, PageForm pageForm) {
        String sql = "select b.isbn,b.book_name,b.author,b.publish,b.price,o.id as order_id,o.book_id,o.flag,o.create_time,o.student_id,o.class_id from book b INNER JOIN orders o on  o.book_id = b.id and o.student_id = '" + studentId + "' order by o.create_time desc ";
        String sql1 = "select count(order_id) from ("+sql+") s;";
        Map<String,Object> map = new HashMap<>();
        Integer rows = namedParameterJdbcTemplate.queryForObject(sql1, map, Integer.class);
        int pages = 0;
        int num = pageForm.getPageNum();
        int size = pageForm.getPageSize();
        if (rows % size ==0){
            pages = rows/size;
        }else{
            pages = rows/size + 1;
        }
        if (num<1){
            sql+="limit 0,"+size+";";
        }else {
            sql+="limit "+(num*size)+","+size+";";
        }
        List<OrderBookQuery> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<OrderBookQuery>(OrderBookQuery.class));
        for (OrderBookQuery bookQuery: query){
            String s = String.valueOf(bookQuery.getCreate_time());
            bookQuery.setFlagdetail(s.substring(0,10)+" "+s.substring(11,19));
        }
        PageListGer<OrderBookQuery> ger = new PageListGer<>();
        ger.setContent(query);
        ger.setTotalElements(rows);
        ger.setNumber(num+1);
        ger.setTotalPages(pages);
        return ger;
    }

    @Override
    public List<OrderBookId> findOrders1(String studentId) {
        String sql = "select book_id as id from orders where student_id = '" + studentId + "';";
        List<OrderBookId> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<OrderBookId>(OrderBookId.class));
        return query;
    }
}
