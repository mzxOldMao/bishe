package mao.bishe.api.service;

import mao.bishe.api.model.Book;
import mao.bishe.api.model.query.TextBookQuery;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<Book> findAll(TextBookQuery textBookQuery);
}
