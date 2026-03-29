package by.slavik.bookcatalog.service.impl;

import by.slavik.bookcatalog.exception.BookNotFoundException;
import by.slavik.bookcatalog.mapper.BookMapper;
import by.slavik.bookcatalog.model.Book;
import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import by.slavik.bookcatalog.repository.BookRepository;
import by.slavik.bookcatalog.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public ResponseBookDto addBook(RequestBookDto requestBookDto) {
        Book saved = bookRepository.save(bookMapper.toEntity(requestBookDto));
        return bookMapper.toResponseDto(saved);
    }

    @Override
    public ResponseBookDto findById(Long id) {
        return bookMapper.toResponseDto(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id)));
    }

    @Override
    public Page<ResponseBookDto> find(String title, Pageable pageable) {
        Page<Book> books;
        if (title == null || title.isBlank()) {
            books = bookRepository.findAll(pageable);
        } else {
            books = bookRepository.findByTitle(title, pageable);
        }

        return books.map(bookMapper::toResponseDto);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
