package finalmission.application;

import finalmission.domain.Book;
import finalmission.infra.thirdparty.AladinRestClient;
import finalmission.infra.thirdparty.AladinSearchResponses;
import finalmission.presentation.request.BookCreateRequest;
import finalmission.presentation.response.BookCreateResponse;
import finalmission.presentation.response.BookResponse;
import finalmission.presentation.response.BookSearchResponse;
import finalmission.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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

    public List<BookResponse> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookResponse::from)
                .toList();
    }

    @Transactional
    public void deleteBook(Long id) {
        Book bookById = findBookById(id);
        bookRepository.deleteById(bookById.getId());
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 등록되지 않은 책입니다."));
    }
}
