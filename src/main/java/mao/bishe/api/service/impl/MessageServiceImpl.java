package mao.bishe.api.service.impl;

import mao.bishe.api.dao.MessageDao;
import mao.bishe.api.model.Message;
import mao.bishe.api.model.form.MessageForm;
import mao.bishe.api.model.query.MessageQuery;
import mao.bishe.api.service.MessageService;
import mao.bishe.api.utils.PageListGer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public void save(MessageForm messageForm,String student_id) {
        Message message = new Message();
        message.setStudentId(student_id);
        message.setInfo(messageForm.getDes());
        message.setWho(messageForm.getRegion());
        message.setName(messageForm.getName());
        message.setType(messageForm.getType());
        message.setFlag(1);
        messageDao.save(message);
        System.out.println(message);
    }

    @Override
    public PageListGer<Message> findAll(String student_id, MessageQuery messageQuery) {
        Specification<Message> specification = new Specification<Message>() {
            List<Predicate> list = new ArrayList<Predicate>();
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> studentId = root.get("studentId");
                Predicate predicate = criteriaBuilder.equal(studentId.as(String.class),student_id);
                list.add(predicate);
                if (messageQuery.getFlag()!=null){
                    Path<Integer> flag1 = root.get("flag");
                    Predicate predicate1 = criteriaBuilder.equal(flag1.as(Integer.class),messageQuery.getFlag());
                    list.add(predicate1);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Message> all = messageDao.findAll(specification, PageRequest.of(messageQuery.getPageNum(), messageQuery.getPageSize(), Sort.Direction.DESC, "createTime"));
        PageListGer<Message> listGer = new PageListGer<>();
        listGer.setContent(all.getContent());
        listGer.setNumber(all.getNumber()+1);
        listGer.setTotalPages(all.getTotalPages());
        listGer.setTotalElements((int) all.getTotalElements());
        List<Message> content = listGer.getContent();
        for (Message message:content){
            if (message.getFlag()==1){
                message.setFlagdetail("未被处理");
            }else {
                message.setFlagdetail("已被处理");
            }
        }
        return listGer;
    }

    @Override
    public Message findById(Long id) {
        return messageDao.findMessageById(id);
    }
}
