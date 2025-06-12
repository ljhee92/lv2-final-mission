package finalmission.application;

import finalmission.infra.thirdparty.AladinRestClient;
import finalmission.infra.thirdparty.AladinSearchResponses;
import finalmission.presentation.response.BookSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final AladinRestClient aladinRestClient;

    public BookService(AladinRestClient aladinRestClient) {
        this.aladinRestClient = aladinRestClient;
    }

    public List<BookSearchResponse> searchBooks(String keyword) {
        AladinSearchResponses searchResponses = aladinRestClient.search(keyword);

        return searchResponses.item().stream()
                .map(BookSearchResponse::from)
                .toList();
    }
}
