package finalmission.presentation.response;

import finalmission.infra.thirdparty.Item;

public record BookSearchResponse(
        String title,
        String author,
        String pubDate,
        String description,
        String image,
        String isbn
) {

    public static BookSearchResponse from(Item item) {
        return new BookSearchResponse(
                item.title(), item.author(), item.pubDate(), item.description(), item.cover(), item.isbn()
        );
    }
}
