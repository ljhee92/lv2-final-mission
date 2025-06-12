package finalmission.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookInformation {

    private Long bookId;

    private String title;

    private String author;

    public static BookInformation from(Book book) {
        return new BookInformation(book.getId(), book.getTitle(), book.getAuthor());
    }
}
