package mao.bishe.api.controller;

import mao.bishe.api.model.Message;
import mao.bishe.api.model.form.MessageForm;
import mao.bishe.api.model.query.MessageQuery;
import mao.bishe.api.service.MessageService;
import mao.bishe.api.utils.PageListGer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/saveMessage")
    public ResponseEntity saveMessage(@RequestBody MessageForm messageForm, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        Map<String, Object> map = new HashMap<>();
        if (userName != null) {
            messageService.save(messageForm, userName);
            map.put("code", 200);
            map.put("message", "添加message成功！");
        } else {
            map.put("message", "添加信息失败！");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getMessages")
    public ResponseEntity getMessages(MessageQuery messageQuery, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        Map<String, Object> map = new HashMap<>();
        if (userName != null) {
            PageListGer<Message> all = messageService.findAll(userName, messageQuery);
            map.put("code", 200);
            map.put("data", all);
            map.put("message", "获取message成功！");
        } else {
            map.put("message", "获取信息失败！");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getMessage/{id}")
    public ResponseEntity getMessage(@PathVariable Long id) {
        Message message = messageService.findById(id);
        if (message.getFlag()==1){
            message.setFlagdetail("未被处理");
        }else {
            message.setFlagdetail("已被处理");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("data", message);
        map.put("message", "获取message成功！");
        return ResponseEntity.ok(map);
    }
}
