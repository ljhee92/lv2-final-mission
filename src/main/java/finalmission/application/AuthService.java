package finalmission.application;

import finalmission.domain.Member;
import finalmission.infra.jwt.JwtTokenProvider;
import finalmission.presentation.request.LoginMember;
import finalmission.presentation.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
            MemberService memberService,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequest loginRequest) {
        Member member = memberService.findMemberByEmail(loginRequest.email());
        checkPassword(member, loginRequest.password());
        LoginMember loginMember = new LoginMember(member.getId(), member.getRole(), member.getName(),  member.getEmail());
        return jwtTokenProvider.createToken(loginMember);
    }

    private void checkPassword(Member member, String password) {
        if (!member.isCorrectPassword(password)) {
            throw new IllegalArgumentException("[ERROR] 올바른 비밀번호가 아닙니다.");
        }
    }

    public Member findMemberByToken(String token) {
        Long memberId = jwtTokenProvider.getId(token);
        return memberService.findMemberById(memberId);
    }
}
