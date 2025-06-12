package finalmission.domain;

import finalmission.exception.AuthException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RoleTest {

    @Test
    void 존재하는_역할이면_역할의_이넘을_반환한다() {
        String admin = "ADMIN";
        String member = "MEMBER";

        assertAll(
                () -> assertThat(Role.from(admin)).isEqualTo(Role.ADMIN),
                () -> assertThat(Role.from(member)).isEqualTo(Role.MEMBER)
        );
    }

    @Test
    void 존재하지_않는_역할이면_예외를_반환한다() {
        String notExist = "NOT_EXIST";

        assertThatThrownBy(() -> Role.from(notExist))
                .isInstanceOf(AuthException.class);
    }
}
