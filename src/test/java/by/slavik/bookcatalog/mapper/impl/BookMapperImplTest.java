package by.slavik.bookcatalog.mapper.impl;

import by.slavik.bookcatalog.model.Book;
import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperImplTest {

    private BookMapperImpl bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapperImpl();
    }

    @Test
    void toEntityTest() {
        RequestBookDto dto = new RequestBookDto(
                "Clean Code",
                "Robert C. Martin",
                2008
        );

        Book entity = bookMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
        assertThat(entity.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(entity.getPublishedYear()).isEqualTo(dto.getPublishedYear());
    }

    @Test
    void toResponseDtoTest() {
        Book book = Book.builder()
                .id(1L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .publishedYear(2018)
                .build();

        ResponseBookDto dto = bookMapper.toResponseDto(book);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(book.getId());
        assertThat(dto.getTitle()).isEqualTo(book.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(dto.getPublishedYear()).isEqualTo(book.getPublishedYear());
    }

}