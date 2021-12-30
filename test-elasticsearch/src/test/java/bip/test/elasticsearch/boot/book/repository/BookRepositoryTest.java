package bip.test.elasticsearch.boot.book.repository;

import bip.test.elasticsearch.boot.book.model.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by ramezani on 12/19/2019.
 */
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void findAll() throws Exception {
        Iterable<Book> books = bookRepository.findAll();
        for (Book book:books) {
            System.out.println("book = " + book);
        }
        System.out.println("BookRepositoryTest.findAll finished.");
    }

}