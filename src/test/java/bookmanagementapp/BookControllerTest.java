package bookmanagementapp;

import bookmanagementapp.controller.BookController;
import bookmanagementapp.dto.BookDto;
import bookmanagementapp.mapper.BookMapper;
import bookmanagementapp.model.Book;
import bookmanagementapp.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() throws Exception {

        BookDto bookDto1 = new BookDto(1L, "Book 1", "Author 1", "ISBN-1");
        BookDto bookDto2 = new BookDto(2L, "Book 2", "Author 2", "ISBN-2");
        List<BookDto> bookDtos =Arrays.asList(bookDto1,bookDto2);

        Mockito.when(bookService.getAllBooks()).thenReturn(bookDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
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
        BookDto bookDto = new BookDto(id, "Book 1", "Author 1", "ISBN-1");

        Mockito.when(bookService.getBookById(id)).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("ISBN-1"));
    }

    @Test
    public void testCreateBook() throws Exception {
        BookDto bookDto = new BookDto(1L, "Book 1", "Author 1", "ISBN-1");
        Mockito.when(bookService.createBook(bookDto)).thenReturn(bookDto);

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
        BookDto updatedBookDto = new BookDto(id, "Updated Book", "Updated Author", "Updated ISBN");
        Mockito.when(bookService.updateBook(id,updatedBookDto)).thenReturn(updatedBookDto);

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