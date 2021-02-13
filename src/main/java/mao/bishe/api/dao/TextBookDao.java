package mao.bishe.api.dao;

import mao.bishe.api.model.TextBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TextBookDao extends JpaRepository<TextBook, Long> {
    //可根据多个条件查询
    Page<TextBook> findAll(Specification specification, Pageable pageable);
}
