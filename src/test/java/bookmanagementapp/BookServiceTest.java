package bookmanagementapp;

import bookmanagementapp.model.Book;
import bookmanagementapp.repository.BookRepository;
import bookmanagementapp.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    public void getAllBooks() {
        // Arrange
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", "ISBN-1"),
                new Book(2L, "Book 2", "Author 2", "ISBN-2")
        );
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("ISBN-1", result.get(0).getIsbn());
        assertEquals("Book 2", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("ISBN-2", result.get(1).getIsbn());
    }

    @Test
    public void getBookById() {
        // Arrange
        Long id = 1L;
        Book book = new Book(id, "Book 1", "Author 1", "ISBN-1");
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(id);

        // Assert
        assertEquals("Book 1", result.getTitle());
        assertEquals("Author 1", result.getAuthor());
        assertEquals("ISBN-1", result.getIsbn());
    }

    @Test
    public void createBook() {
        // Arrange
        Book book = new Book(null, "New Book", "New Author", "New ISBN");
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book result = bookService.createBook(book);

        // Assert
        assertEquals("New Book", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        assertEquals("New ISBN", result.getIsbn());
    }

    @Test
    public void updateBook() {
        // Arrange
        Long id = 1L;
        Book existingBook = new Book(id, "Book 1", "Author 1", "ISBN-1");
        Book updatedBook = new Book(id, "Updated Book", "Updated Author", "Updated ISBN");
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Act
        Book result = bookService.updateBook(id, updatedBook);

        // Assert
        assertEquals("Updated Book", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals("Updated ISBN", result.getIsbn());
    }

    @Test
    public void deleteBook() {
        // Arrange
        Long id = 1L;
        Book book = new Book(id, "Book 1", "Author 1", "ISBN-1");
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        Assertions.assertDoesNotThrow(() -> bookService.deleteBook(id));

        // Assert
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
    }
}