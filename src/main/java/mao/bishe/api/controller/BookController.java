package mao.bishe.api.controller;

import mao.bishe.api.model.Book;
import mao.bishe.api.model.query.TextBookQuery;
import mao.bishe.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getbooks")
    public ResponseEntity getbooks(TextBookQuery textBookQuery){
        Page<Book> all = bookService.findAll(textBookQuery);
        int number = all.getNumber();
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("data",all);
        map.put("number",number+1);
        map.put("message","请求成功");
        return ResponseEntity.ok(map);
    }
}
