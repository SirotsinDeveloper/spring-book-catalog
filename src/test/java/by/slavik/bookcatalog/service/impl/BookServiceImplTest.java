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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private final Long ID = 1L;
    private final Book BOOK = new Book();

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 10;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook() {
        RequestBookDto request = mock(RequestBookDto.class);
        ResponseBookDto response = ResponseBookDto.builder()
                .id(ID)
                .title("Test")
                .author("Author")
                .publishedYear(2020)
                .build();

        when(bookMapper.toEntity(request)).thenReturn(BOOK);
        when(bookRepository.save(BOOK)).thenReturn(BOOK);
        when(bookMapper.toResponseDto(BOOK)).thenReturn(response);

        ResponseBookDto result = bookService.addBook(request);

        assertThat(result).isEqualTo(response);

        verify(bookRepository).save(BOOK);
        verify(bookMapper).toEntity(request);
        verify(bookMapper).toResponseDto(BOOK);

        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void getBookById() {
        ResponseBookDto response = ResponseBookDto.builder().id(ID).build();

        when(bookRepository.findById(ID)).thenReturn(Optional.of(BOOK));
        when(bookMapper.toResponseDto(BOOK)).thenReturn(response);

        ResponseBookDto result = bookService.findById(ID);

        assertThat(result).isEqualTo(response);
        verify(bookRepository).findById(ID);

        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void getAllBooksWhenTitleIsNull() {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);

        Page<Book> page = new PageImpl<>(java.util.List.of(BOOK));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toResponseDto(BOOK)).thenReturn(ResponseBookDto.builder().build());

        Page<ResponseBookDto> result = bookService.findFilteredBooks(null, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findAll(pageable);

        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void findFilteredBooksWhenTitleProvided() {
        String title = "Test";
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);

        Page<Book> page = new PageImpl<>(java.util.List.of(BOOK));

        when(bookRepository.findByTitle(title, pageable)).thenReturn(page);
        when(bookMapper.toResponseDto(BOOK)).thenReturn(ResponseBookDto.builder().build());

        Page<ResponseBookDto> result = bookService.findFilteredBooks(title, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(bookRepository).findByTitle(title, pageable);

        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    void deleteBook() {
        bookService.deleteById(ID);

        verify(bookRepository).deleteById(ID);

        verifyNoMoreInteractions(bookRepository);
    }

}