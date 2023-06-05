package bookmanagementapp.service;

import bookmanagementapp.dto.BookDto;
import bookmanagementapp.mapper.BookMapper;
import bookmanagementapp.model.Book;
import bookmanagementapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            BookDto bookDto = bookMapper.toDto(book);
            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    public BookDto getBookById(Long id) throws  IllegalArgumentException {
        Book book = bookRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return bookMapper.toDto(book);
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public BookDto updateBook(Long id, BookDto bookDetails) throws IllegalArgumentException {
        Book existingBook = bookRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Book not found with id" + id));

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setIsbn(bookDetails.getIsbn());

        Book savedBook = bookRepository.save(existingBook);

        return bookMapper.toDto(savedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
