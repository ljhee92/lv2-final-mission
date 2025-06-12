package finalmission.application;

import finalmission.domain.Book;
import finalmission.infra.thirdparty.AladinRestClient;
import finalmission.infra.thirdparty.AladinSearchResponses;
import finalmission.presentation.request.BookCreateRequest;
import finalmission.presentation.response.BookCreateResponse;
import finalmission.presentation.response.BookSearchResponse;
import finalmission.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final AladinRestClient aladinRestClient;
    private final BookRepository bookRepository;

    public BookService(
            AladinRestClient aladinRestClient,
            BookRepository bookRepository
    ) {
        this.aladinRestClient = aladinRestClient;
        this.bookRepository = bookRepository;
    }

    public List<BookSearchResponse> searchBooks(String keyword) {
        AladinSearchResponses searchResponses = aladinRestClient.search(keyword);

        return searchResponses.item().stream()
                .map(BookSearchResponse::from)
                .toList();
    }

    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request) {
        String title = request.title();
        String author = request.author();
        LocalDate pubDate = request.pubDate();
        String description = request.description();
        String image = request.image();
        String isbn = request.isbn();
        int totalQuantity = request.totalQuantity();

        Book bookWithoutId = Book.create(title, author, pubDate, description, image, isbn, totalQuantity);
        Book bookWithId = bookRepository.save(bookWithoutId);
        return BookCreateResponse.from(bookWithId);
    }
}
