package by.slavik.bookcatalog.service.impl;

import by.slavik.bookcatalog.mapper.BookMapper;
import by.slavik.bookcatalog.model.Book;
import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import by.slavik.bookcatalog.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook() {
        RequestBookDto request = mock(RequestBookDto.class);
        Book book = new Book();
        ResponseBookDto response = ResponseBookDto.builder()
                .id(1L)
                .title("Test")
                .author("Author")
                .publishedYear(2020)
                .build();

        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(response);

        ResponseBookDto result = bookService.addBook(request);

        assertThat(result).isEqualTo(response);

        verify(bookRepository).save(book);
        verify(bookMapper).toEntity(request);
        verify(bookMapper).toResponseDto(book);
    }

    @Test
    void getBookById() {
        Long id = 1L;
        Book book = new Book();
        ResponseBookDto response = ResponseBookDto.builder().id(id).build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(response);

        ResponseBookDto result = bookService.findById(id);

        assertThat(result).isEqualTo(response);
        verify(bookRepository).findById(id);
    }

    @Test
    void getAllBooksWhenTitleIsNull() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        Page<Book> page = new PageImpl<>(java.util.List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toResponseDto(book)).thenReturn(ResponseBookDto.builder().build());

        Page<ResponseBookDto> result = bookService.findFilteredBooks(null, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findAll(pageable);
    }

    @Test
    void findFilteredBooksWhenTitleProvided() {
        String title = "Test";
        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        Page<Book> page = new PageImpl<>(java.util.List.of(book));

        when(bookRepository.findByTitle(title, pageable)).thenReturn(page);
        when(bookMapper.toResponseDto(book)).thenReturn(ResponseBookDto.builder().build());

        Page<ResponseBookDto> result = bookService.findFilteredBooks(title, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findByTitle(title, pageable);
    }

    @Test
    void deleteBook() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.of(new Book()));

        boolean result = bookService.deleteById(id);

        assertThat(result).isTrue();
        verify(bookRepository).deleteById(id);
    }
}