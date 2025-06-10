package finalmission.application;

import finalmission.infra.thirdparty.AladinRestClient;
import finalmission.infra.thirdparty.AladinSearchResponse;
import finalmission.presentation.response.BookSearchResponse;
import finalmission.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final AladinRestClient aladinRestClient;

    public BookService(AladinRestClient aladinRestClient) {
        this.aladinRestClient = aladinRestClient;
    }

    public List<BookSearchResponse> searchBooks(String keyword) {
        AladinSearchResponse searchResponse = aladinRestClient.search(keyword);

        return searchResponse.item().stream()
                .map(BookSearchResponse::from)
                .toList();
    }
}
