package by.slavik.bookcatalog.service;

import by.slavik.bookcatalog.model.dto.RequestBookDto;

import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    ResponseBookDto addBook(RequestBookDto bookDto);

    ResponseBookDto findById(Long id);

    Page<ResponseBookDto> findFilteredBooks(String title, Pageable pageable);

    void deleteById(Long id);
}
