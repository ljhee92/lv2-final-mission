package finalmission.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MemberTest {

    @Test
    void 사용자를_생성한다() {
        String name = "사용자";
        String email = "member1@email.com";
        String password = "password";

        Member created = Member.createUser(name, email, password);

        assertAll(
                () -> assertThat(created.getRole()).isEqualTo(Role.USER),
                () -> assertThat(created.getName()).isEqualTo(name),
                () -> assertThat(created.getEmail()).isEqualTo(email),
                () -> assertThat(created.getPassword()).isEqualTo(password)
        );
    }

    @Test
    void 관리자를_생성한다() {
        String name = "관리자";
        String email = "admin@email.com";
        String password = "passwordAdmin";

        Member created = Member.createAdmin(name, email, password);

        assertAll(
                () -> assertThat(created.getRole()).isEqualTo(Role.ADMIN),
                () -> assertThat(created.getName()).isEqualTo(name),
                () -> assertThat(created.getEmail()).isEqualTo(email),
                () -> assertThat(created.getPassword()).isEqualTo(password)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "김"})
    void 사용자_이름이_형식에_맞지않으면_예외가_발생한다(String name) {
        String email = "member1@email.com";
        String password = "password";

        assertThatThrownBy(() -> Member.createUser(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "김"})
    void 관리자_이름이_형식에_맞지않으면_예외가_발생한다(String name) {
        String email = "admin@email.com";
        String password = "passwordAdmin";

        assertThatThrownBy(() -> Member.createAdmin(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1.com", "email.com", "email@test"})
    void 사용자_이메일이_형식에_맞지않으면_예외가_발생한다(String email) {
        String name = "사용자";
        String password = "password";

        assertThatThrownBy(() -> Member.createUser(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1.com", "email.com", "email@test"})
    void 관리자_이메일이_형식에_맞지않으면_예외가_발생한다(String email) {
        String name = "관리자";
        String password = "passwordAdmin";

        assertThatThrownBy(() -> Member.createAdmin(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "1234567", "passwor"})
    void 사용자_비밀번호가_형식에_맞지않으면_예외가_발생한다(String password) {
        String name = "사용자";
        String email = "member1@email.com";

        assertThatThrownBy(() -> Member.createUser(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "1234567", "passwor"})
    void 관리자_비밀번호가_형식에_맞지않으면_예외가_발생한다(String password) {
        String name = "관리자";
        String email = "admin@email.com";

        assertThatThrownBy(() -> Member.createAdmin(name, email, password))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
