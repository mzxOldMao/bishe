package mao.bishe.api.service.impl;

import mao.bishe.api.dao.TextBookDao;
import mao.bishe.api.model.TextBook;
import mao.bishe.api.model.query.TextBookQuery;
import mao.bishe.api.service.TextBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextBookServiceImpl implements TextBookService {
    @Autowired
    private TextBookDao textBookDao;

    @Override
    public Page<TextBook> findAll(TextBookQuery textBookQuery) {
        Specification<TextBook> specification = new Specification<TextBook>() {
            @Override
            public Predicate toPredicate(Root<TextBook> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!ObjectUtils.isEmpty(textBookQuery.getCampus())){
                    Path<String> campus = root.get("campus");
                    Predicate predicate1 = cb.equal(campus.as(String.class),textBookQuery.getCampus());
                    list.add(predicate1);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getCourseUnit())){
                    Path<String> courseUnit = root.get("courseUnit");
                    Predicate predicate2 = cb.equal(courseUnit.as(String.class),textBookQuery.getCourseUnit());
                    list.add(predicate2);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getCourseName())){
                    Path<String> courseName = root.get("courseName");
                    Predicate predicate3 = cb.like(courseName.as(String.class),"%"+textBookQuery.getCourseName()+"%");
                    list.add(predicate3);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getBookName())){
                    Path<String> bookName = root.get("bookName");
                    Predicate predicate4 = cb.like(bookName.as(String.class), "%" + textBookQuery.getBookName() + "%");
                    list.add(predicate4);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getAuthor())){
                    Path<String> author = root.get("author");
                    Predicate predicate5 = cb.like(author.as(String.class), "%" + textBookQuery.getAuthor() + "%");
                    list.add(predicate5);
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        Page<TextBook> textBooks = textBookDao.findAll(specification, PageRequest.of(textBookQuery.getPageNum(), textBookQuery.getPageSize(), Sort.Direction.ASC, "id"));
        return textBooks;
    }
}
