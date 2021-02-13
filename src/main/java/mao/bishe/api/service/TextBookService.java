package mao.bishe.api.service;

import mao.bishe.api.model.TextBook;
import mao.bishe.api.model.query.TextBookQuery;
import org.springframework.data.domain.Page;

public interface TextBookService {
    Page<TextBook> findAll(TextBookQuery textBookQuery);
}
