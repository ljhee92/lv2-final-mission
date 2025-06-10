package finalmission.presentation.request;

import finalmission.domain.Role;

public record LoginMember(
        Long id,
        Role role,
        String name,
        String email
) {
}
