package bookManagement.sb_app;

import bookManagement.sb_app.Dto.UserDto;
import bookManagement.sb_app.controller.BookController;
import bookManagement.sb_app.controller.UserController;
import bookManagement.sb_app.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import bookManagement.sb_app.entity.Book;
import bookManagement.sb_app.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SbAppApplicationTests {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Mock
	private Model model;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testShowRegistrationForm() {
		String viewName = userController.showRegistrationForm(model);
		verify(model).addAttribute(eq("user"), any(UserDto.class));
		assert(viewName).equals("register");
	}

	@Test
	void testRegisterUserSuccess() {
		UserDto userDto = new UserDto();
		userDto.setUsername("testUser");
		when(userService.isUsernameUnique("testUser")).thenReturn(true);

		String viewName = userController.registerUser(userDto, model);

		verify(userService).addUser(userDto);
		assert(viewName).equals("redirect:/login");
	}

	@Test
	void testRegisterUserFailure() {
		UserDto userDto = new UserDto();
		userDto.setUsername("existingUser");
		when(userService.isUsernameUnique("existingUser")).thenReturn(false);

		String viewName = userController.registerUser(userDto, model);

		verify(model).addAttribute(eq("error"), any(String.class));
		assert(viewName).equals("register");
	}


    @Nested
    class BookControllerTest {

		@Mock
		private BookService bookService;

		@InjectMocks
		private BookController bookController;

		@Mock
		private Model model;

		@BeforeEach
		void setUp() {
			MockitoAnnotations.initMocks(this);
		}

		@Test
		void testSaveBook() {
			Book book = new Book();
			book.setTitle("Test Book");
			when(bookService.saveBook(any(Book.class))).thenReturn(book);

			String viewName = bookController.saveBook(book);

			verify(bookService).saveBook(any(Book.class));
			assert(viewName).equals("redirect:/books");
		}

		@Test
		void testEditBookForm() {
			Long bookId = 1L;
			Book book = new Book();
			book.setId(bookId);
			when(bookService.getBookById(bookId)).thenReturn(book);

			String viewName = bookController.editBookForm(bookId, model);

			verify(model).addAttribute(eq("book"), any(Book.class));
			assert(viewName).equals("edit_book");
		}

		@Test
		void testUpdateBook() {
			Long bookId = 1L;
			Book book = new Book();
			book.setId(bookId);
			book.setTitle("Updated Title");
			when(bookService.getBookById(bookId)).thenReturn(book);

			String viewName = bookController.updateBook(bookId, book, model);

			verify(bookService).updateBook(any(Book.class));
			assert(viewName).equals("redirect:/books");
		}

		@Test
		void testDeleteBook() {
			Long bookId = 1L;

			String viewName = bookController.deleteBook(bookId, model);

			verify(bookService).deleteBookById(bookId);
			assert(viewName).equals("redirect:/books");
		}



	}

}
