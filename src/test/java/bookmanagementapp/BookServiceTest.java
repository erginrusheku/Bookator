package bookmanagementapp;

import bookmanagementapp.dto.BookDto;
import bookmanagementapp.mapper.BookMapper;
import bookmanagementapp.model.Book;
import bookmanagementapp.repository.BookRepository;
import bookmanagementapp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllBooks() {
        // Arrange
        Book book1 = new Book(1L, "Book 1", "Author 1", "ISBN-1");
        Book book2 = new Book(2L, "Book 2", "Author 2", "ISBN-2");
        List<Book> books =Arrays.asList(book1,book2);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookMapper.toDto(book1)).thenReturn(new BookDto(1L, "Book 1", "Author 1", "ISBN-1"));
        Mockito.when(bookMapper.toDto(book2)).thenReturn(new BookDto(2L, "Book 2", "Author 2", "ISBN-2"));
        // Act
        List<BookDto> result = bookService.getAllBooks();

        // Assert
        assertEquals(books.size(), result.size());
    }

    @Test
    public void getBookById() {

        Long id = 1L;
        Book book = new Book(id, "Book 1", "Author 1", "ISBN-1");
        BookDto bookDto = new BookDto(id,"Book 1", "Author 1", "ISBN-1");

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getBookById(id);

        assertEquals(bookDto,result);
    }

    @Test
    public void createBook() {
        // Arrange
        BookDto bookDto = new BookDto(1L, "New Book", "New Author", "New ISBN");
        Book book = new Book(1L, "New Book", "New Author", "New ISBN");
        Mockito.when(bookMapper.toEntity(bookDto)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        // Act
        BookDto result = bookService.createBook(bookDto);

        // Assert
        assertEquals(bookDto, result);
    }

    @Test
    public void updateBook() {
        // Arrange
        Long id = 1L;
        BookDto updatedBookDto = new BookDto(id, "Book 2", "Author 2", "ISBN-2");
        Book updatedBook = new Book(id, "Book 2", "Author 2", "ISBN-2");

        when(bookRepository.findById(id)).thenReturn(Optional.of(new Book()));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(updatedBookDto);

        // Act
        BookDto result = bookService.updateBook(id, updatedBookDto);

        // Assert
        assertEquals(updatedBookDto, result);
    }

    @Test
    public void deleteBook() {

        Long id = 1L;

        // Act
        bookService.deleteBook(id);

        // Assert
        verify(bookRepository, times(1)).deleteById(id);
    }
}