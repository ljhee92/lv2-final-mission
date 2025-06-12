package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private static final int NAME_MIN_LENGTH = 2;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    private String email;

    private String password;

    public static Member createMember(String name, String email, String password) {
        validate(name, email, password);
        return new Member(null, Role.MEMBER, name, email, password);
    }

    public static Member createAdmin(String name, String email, String password) {
        validate(name, email, password);
        return new Member(null, Role.ADMIN, name, email, password);
    }

    public boolean isCorrectPassword(String password) {
        return password.equals(this.password);
    }

    private static void validate(String name, String email, String password) {
        validateName(name);
        validateEmail(email);
        validatePassword(password);
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수 입력사항입니다.");
        }

        if (name.length() < NAME_MIN_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 두 자 이상 입력해야 합니다.");
        }
    }

    private static void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("[ERROR] 이메일은 필수 입력사항입니다.");
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 이메일 형식이 올바르지 않습니다.");
        }
    }

    private static void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("[ERROR] 비밀번호는 필수 입력사항입니다.");
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 비밀번호는 여덟 자 이상 입력해야 합니다.");
        }
    }
}
