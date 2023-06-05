package bookmanagementapp.mapper;

import bookmanagementapp.dto.BookDto;
import bookmanagementapp.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        return bookDto;
    }

    public Book toEntity(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        return book;
    }
}
