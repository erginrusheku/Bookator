package bookmanagementapp.controller;

import bookmanagementapp.dto.BookDto;
import bookmanagementapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id){ return bookService.getBookById(id);}

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
