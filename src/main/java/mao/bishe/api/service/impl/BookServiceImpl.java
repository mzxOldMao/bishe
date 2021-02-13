package mao.bishe.api.service.impl;

import mao.bishe.api.dao.BookDao;
import mao.bishe.api.model.Book;
import mao.bishe.api.model.query.TextBookQuery;
import mao.bishe.api.service.BookService;
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
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Page<Book> findAll(TextBookQuery textBookQuery) {
        Specification<Book> specification = new Specification<Book>() {
            List<Predicate> list = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!ObjectUtils.isEmpty(textBookQuery.getBookName())){
                    Path<String> bookName = root.get("bookName");
                    Predicate predicate = criteriaBuilder.like(bookName.as(String.class),"%"+textBookQuery.getBookName()+"%");
                    list.add(predicate);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getAuthor())){
                    Path<String> author = root.get("author");
                    Predicate predicate = criteriaBuilder.like(author.as(String.class),"%"+textBookQuery.getAuthor()+"%");
                    list.add(predicate);
                }
                if (!ObjectUtils.isEmpty(textBookQuery.getStart()) && !ObjectUtils.isEmpty(textBookQuery.getEnd())){
                    Path<Double> price = root.get("price");
                    Predicate predicate1 = criteriaBuilder.gt(price.as(Double.class),textBookQuery.getStart());
                    Predicate predicate2 = criteriaBuilder.le(price.as(Double.class),textBookQuery.getEnd());
                    list.add(predicate1);
                    list.add(predicate2);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        Page<Book> books = bookDao.findAll(specification, PageRequest.of(textBookQuery.getPageNum(), textBookQuery.getPageSize(), Sort.Direction.ASC, "id"));
        return books;
    }
}



