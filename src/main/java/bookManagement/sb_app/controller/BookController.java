package bookManagement.sb_app.controller;

import bookManagement.sb_app.entity.Book;
import bookManagement.sb_app.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        super();
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/new")
    public String createBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book",book);
        return "create_book";
    }


    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";

    }

    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return"edit_book";
    }
    @PostMapping("/books/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        Book existingBook = bookService.getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        bookService.updateBook(existingBook);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

}
