package bookmanagementapp;

import bookmanagementapp.controller.BookController;
import bookmanagementapp.model.Book;
import bookmanagementapp.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", "ISBN-1"),
                new Book(2L, "Book 2", "Author 2", "ISBN-2")
        );
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("ISBN-1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].isbn").value("ISBN-2"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Long id = 1L;
        Book book = new Book(id, "Book 1", "Author 1", "ISBN-1");
        Mockito.when(bookService.getBookById(id)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("ISBN-1"));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book(1L, "Book 1", "Author 1", "ISBN-1");
        Mockito.when(bookService.createBook(Mockito.any(Book.class))).thenReturn(book);

        String bookJson = "{\"id\": 1, \"title\": \"Book 1\", \"author\": \"Author 1\", \"isbn\": \"ISBN-1\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("ISBN-1"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long id = 1L;
        Book updatedBook = new Book(id, "Updated Book", "Updated Author", "Updated ISBN");
        Mockito.when(bookService.updateBook(Mockito.anyLong(), Mockito.any(Book.class))).thenReturn(updatedBook);

        String updatedBookJson = "{\"id\": 1, \"title\": \"Updated Book\", \"author\": \"Updated Author\", \"isbn\": \"Updated ISBN\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Updated Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("Updated ISBN"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(bookService, Mockito.times(1)).deleteBook(id);
    }
}