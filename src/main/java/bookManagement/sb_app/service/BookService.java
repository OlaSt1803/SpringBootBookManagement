package bookManagement.sb_app.service;

import bookManagement.sb_app.entity.Book;

import java.util.List;

public interface BookService  {
    List<Book> getAllBooks();

    Book saveBook(Book book);

    Book getBookById(Long id);
    Book updateBook(Book book);

    void deleteBookById(Long id);

}
