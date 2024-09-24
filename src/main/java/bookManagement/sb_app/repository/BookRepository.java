package bookManagement.sb_app.repository;

import bookManagement.sb_app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
