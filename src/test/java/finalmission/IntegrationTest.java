package finalmission;

import finalmission.domain.Member;
import finalmission.fixture.MemberFixture;
import finalmission.presentation.request.LoginRequest;
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

import static org.hamcrest.Matchers.notNullValue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberFixture memberFixture;

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
                .statusCode(HttpStatus.OK.value());
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

    private String getToken(LoginRequest loginRequest) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");
    }
}
