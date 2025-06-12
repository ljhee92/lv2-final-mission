package finalmission;

import finalmission.domain.Book;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.fixture.BookFixture;
import finalmission.fixture.MemberFixture;
import finalmission.fixture.ReservationFixture;
import finalmission.presentation.request.BookCreateRequest;
import finalmission.presentation.request.LoginRequest;
import finalmission.presentation.request.ReservationCreateRequest;
import finalmission.presentation.response.ReservationCreateResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberFixture memberFixture;

    @Autowired
    private BookFixture bookFixture;

    @Autowired
    private ReservationFixture reservationFixture;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 사용자_로그인_성공시_토큰을_반환한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("token", notNullValue());
    }

    @Test
    void 관리자_로그인_성공시_토큰을_반환한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("token", notNullValue());
    }

    @Test
    void 존재하지_않는_이메일로_로그인하면_예외를_응답한다() {
        Member member = memberFixture.createMember1();
        String notExistEmail = "notExistEmail@email.com";
        LoginRequest loginRequest = new LoginRequest(notExistEmail, member.getPassword());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 올바르지않은_비밀번호로_로그인하면_예외를_응답한다() {
        Member admin = memberFixture.createAdmin();
        String wrongPassword = "wrongPassword";
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), wrongPassword);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 로그아웃에_성공한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        RestAssured.given().log().all()
                .cookie("token", token)
                .when().post("/logout")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 로그인_여부를_확인한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/login/check")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 토큰이_없다면_예외를_반환한다() {
        RestAssured.given().log().all()
                .when().get("/login/check")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 유효하지_않은_토큰이면_예외를_반환한다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", "wrongToken")
                .when().get("/login/check")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 관리자가_도서를_검색한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());
        String token = getToken(loginRequest);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .param("keyword", "오브젝트")
                .when().get("/admin/books")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void 사용자가_도서를_검색하면_예외를_응답한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .param("keyword", "오브젝트")
                .when().get("/admin/books")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 관리자가_도서를_등록한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());
        String token = getToken(loginRequest);

        BookCreateRequest request = getBookCreateRequestOfObject();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/admin/books")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 사용자가_도서를_등록하면_예외를_반환한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        BookCreateRequest request = getBookCreateRequestOfObject();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/admin/books")
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 관리자가_등록된_도서리스트를_조회한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());
        String token = getToken(loginRequest);

        bookFixture.createBook1();
        bookFixture.createBook2();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/books")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @Test
    void 사용자가_등록된_도서리스트를_조회한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        bookFixture.createBook1();
        bookFixture.createBook2();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/books")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @Test
    void 관리자가_등록된_도서를_삭제한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());
        String token = getToken(loginRequest);

        Book book1 = bookFixture.createBook1();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/admin/books/" + book1.getId())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 관리자가_등록되지_않은_도서를_삭제하면_예외가_발생한다() {
        Member admin = memberFixture.createAdmin();
        LoginRequest loginRequest = new LoginRequest(admin.getEmail(), admin.getPassword());
        String token = getToken(loginRequest);

        Book book1 = bookFixture.createBook1();
        Long notExistId = book1.getId() + 1;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/admin/books/" + notExistId)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 사용자가_등록된_도서를_삭제하면_예외가_발생한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        Book book1 = bookFixture.createBook1();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/admin/books/" + book1.getId())
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void 사용자가_도서를_예약한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        Book book1 = bookFixture.createBook1();

        LocalDate reserveDate = LocalDate.now();
        ReservationCreateRequest request = new ReservationCreateRequest(member.getEmail(), reserveDate, book1.getId());
        ReservationCreateResponse expectedResponse = new ReservationCreateResponse(
                1L,
                member.getEmail(),
                reserveDate,
                reserveDate.plusDays(7),
                book1.getId()
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 사용자가_자신의_도서예약을_취소한다() {
        Member member = memberFixture.createMember1();
        LoginRequest loginRequest = new LoginRequest(member.getEmail(), member.getPassword());
        String token = getToken(loginRequest);

        Reservation reservation = reservationFixture.createReservation1();
        Long reservationId = reservation.getId();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/reservations/" + reservationId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private String getToken(LoginRequest loginRequest) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");
    }

    private BookCreateRequest getBookCreateRequestOfObject() {
        String title = "오브젝트";
        String author = "조영호 (지은이)";
        LocalDate pubDate = LocalDate.of(2019, 6, 17);
        String description = "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.";
        String image = "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg";
        String isbn = "K972635015";
        int totalQuantity = 2;
        return new BookCreateRequest(title, author, pubDate, description, image, isbn, totalQuantity);
    }
}
