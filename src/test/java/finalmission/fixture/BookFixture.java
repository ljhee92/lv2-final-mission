package finalmission.fixture;

import finalmission.domain.Book;
import finalmission.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookFixture {

    private final BookRepository bookRepository;

    public BookFixture(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook1() {
        String title = "오브젝트";
        String author = "조영호 (지은이)";
        LocalDate pubDate = LocalDate.of(2019, 6, 17);
        String description = "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.";
        String image = "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg";
        String isbn = "K972635015";
        int totalQuantity = 2;
        Book bookWithoutId = Book.create(title, author, pubDate, description, image, isbn, totalQuantity);
        return bookRepository.save(bookWithoutId);
    }

    public Book createBook2() {
        String title = "좋은 코드, 나쁜 코드 - 프로그래머의 코드 품질 개선법, 2023년 세종도서 학술부문 추천도서";
        String author = "톰 롱 (지은이), 차건회 (옮긴이)";
        LocalDate pubDate = LocalDate.of(2022, 5, 26);
        String description = "구글 엔지니어가 말하는 좋은 코드 작성법. 좋은 코드를 작성하기 위한 이론과 실전을 소개한다. 단순히 해야 할 일과 하지 말아야 할 일을 나열하기보다, 여섯 가지 원칙을 바탕으로 각 개념과 기술의 장단점, 그리고 이면의 핵심 논리를 설명한다.";
        String image = "https://image.aladin.co.kr/product/29464/92/coversum/k422837236_1.jpg";
        String isbn = "K422837236";
        int totalQuantity = 2;
        Book bookWithoutId = Book.create(title, author, pubDate, description, image, isbn, totalQuantity);
        return bookRepository.save(bookWithoutId);
    }
}
