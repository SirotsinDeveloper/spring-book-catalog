package by.slavik.bookcatalog.controller;

import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import by.slavik.bookcatalog.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseBookDto sampleResponse;
    private RequestBookDto sampleRequest;

    @BeforeEach
    void setUp() {
        sampleRequest = new RequestBookDto("Clean Code", "Robert C. Martin", 2008);
        sampleResponse = ResponseBookDto.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .publishedYear(2008)
                .build();
    }

    @Test
    void addBook() throws Exception {
        Mockito.when(bookService.addBook(any(RequestBookDto.class)))
                .thenReturn(sampleResponse);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleResponse.getId()))
                .andExpect(jsonPath("$.title").value(sampleResponse.getTitle()))
                .andExpect(jsonPath("$.author").value(sampleResponse.getAuthor()))
                .andExpect(jsonPath("$.publishedYear").value(sampleResponse.getPublishedYear()));
    }

    @Test
    void getBook() throws Exception {
        Mockito.when(bookService.findById(1L)).thenReturn(sampleResponse);

        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleResponse.getId()))
                .andExpect(jsonPath("$.title").value(sampleResponse.getTitle()))
                .andExpect(jsonPath("$.author").value(sampleResponse.getAuthor()))
                .andExpect(jsonPath("$.publishedYear").value(sampleResponse.getPublishedYear()));
    }

    @Test
    void getFilteredBooks() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(bookService.findFilteredBooks(eq(null), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleResponse), pageable, 1));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(sampleResponse.getId()))
                .andExpect(jsonPath("$.content[0].title").value(sampleResponse.getTitle()));
    }

    @Test
    void deleteById() throws Exception {
        Mockito.when(bookService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}