package finalmission.presentation.response;

import finalmission.infra.thirdparty.AladinSearchResponse;

public record BookSearchResponse(
        String title,
        String author,
        String pubDate,
        String description,
        String image,
        String isbn
) {

    public static BookSearchResponse from(AladinSearchResponse aladinSearchResponse) {
        return new BookSearchResponse(
                aladinSearchResponse.title(), aladinSearchResponse.author(), aladinSearchResponse.pubDate(), aladinSearchResponse.description(), aladinSearchResponse.cover(), aladinSearchResponse.isbn()
        );
    }
}
