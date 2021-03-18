package mao.bishe.api.service;

import mao.bishe.api.model.Message;
import mao.bishe.api.model.form.MessageForm;
import mao.bishe.api.model.query.MessageQuery;
import mao.bishe.api.utils.PageListGer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MessageService {
    void save(MessageForm messageForm,String student_id);
    PageListGer<Message> findAll(String student_id, MessageQuery messageQuery);
    //信息详情
    Message findById(Long id);
}
