package finalmission.presentation;

import finalmission.application.BookService;
import finalmission.presentation.request.BookCreateRequest;
import finalmission.presentation.request.LoginMember;
import finalmission.presentation.response.BookCreateResponse;
import finalmission.presentation.response.BookSearchResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin/books")
    public ResponseEntity<List<BookSearchResponse>> searchBooks(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(required = false) String keyword
    ) {
        List<BookSearchResponse> responses = bookService.searchBooks(keyword);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/admin/books")
    public ResponseEntity<BookCreateResponse> registerBook(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody @Valid BookCreateRequest request
    ) {
        BookCreateResponse response = bookService.createBook(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
