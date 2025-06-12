package finalmission.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.domain.Book;

import java.time.LocalDate;

public record BookCreateResponse(
        Long id,

        String title,

        String author,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate pubDate,

        String description,

        String image,

        String isbn,

        int totalQuantity
) {

        public static BookCreateResponse from(Book book) {
                Long id = book.getId();
                String title = book.getTitle();
                String author = book.getAuthor();
                LocalDate pubDate = book.getPubDate();
                String description = book.getDescription();
                String image = book.getImage();
                String isbn = book.getIsbn();
                int totalQuantity = book.getTotalQuantity();

                return new BookCreateResponse(id, title, author, pubDate, description, image, isbn, totalQuantity);
        }
}
