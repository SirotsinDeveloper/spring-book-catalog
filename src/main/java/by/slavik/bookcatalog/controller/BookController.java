package by.slavik.bookcatalog.controller;

import by.slavik.bookcatalog.model.dto.RequestBookDto;
import by.slavik.bookcatalog.model.dto.ResponseBookDto;
import by.slavik.bookcatalog.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ResponseBookDto> addBook(@RequestBody @Valid RequestBookDto bookDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.addBook(bookDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseBookDto>> getFilteredBooks(
            @RequestParam(required = false) String title,
            Pageable pageable
    ){
        return ResponseEntity.ok(bookService.findFilteredBooks(title, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
