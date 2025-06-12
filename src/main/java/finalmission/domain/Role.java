package finalmission.domain;

import finalmission.exception.AuthException;

import java.util.Arrays;

public enum Role {

    ADMIN,
    MEMBER
    ;

    public static Role from(String name) {
        return Arrays.stream(values())
                .filter(role -> name.equals(role.name()))
                .findFirst()
                .orElseThrow(() -> new AuthException("[ERROR] 유효하지 않은 권한입니다."));
    }
}
