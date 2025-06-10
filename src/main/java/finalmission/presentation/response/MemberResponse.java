package finalmission.presentation.response;

import finalmission.presentation.request.LoginMember;

public record MemberResponse(
        Long id,
        String name,
        String email
) {

    public static MemberResponse from(LoginMember loginMember) {
        return new MemberResponse(loginMember.id(), loginMember.name(), loginMember.email());
    }
}
