package mao.bishe.api.controller;

import mao.bishe.api.model.TextBook;
import mao.bishe.api.model.query.TextBookQuery;
import mao.bishe.api.service.TextBookService;
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
@RequestMapping("/textbook")
public class TextBookController {
    @Autowired
    private TextBookService textBookService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getTexts")
    public ResponseEntity getTexts(TextBookQuery textBookQuery){
        Page<TextBook> all = textBookService.findAll(textBookQuery);
        int number = all.getNumber();
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("data",all);
        map.put("number",number+1);
        map.put("message","请求成功");
        return ResponseEntity.ok(map);
    }
}
