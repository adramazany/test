package bip.test.elasticsearch.boot.book.model;

/**
 * Created by ramezani on 6/13/2018.
 */
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "mkyong", type = "books")
public class Book {

/*
    @Id
    private String id;
    private String title;
    private String author;
    private String releaseDate;

    public Book() {
    }

    public Book(String id, String title, String author, String releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    //getters and setters

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
*/
}
