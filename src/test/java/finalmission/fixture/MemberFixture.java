package finalmission.fixture;

import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberFixture {

    private final MemberRepository memberRepository;

    public MemberFixture(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember1() {
        String name = "사용자";
        String email = "member1@email.com";
        String password = "password";
        return memberRepository.save(Member.createMember(name, email, password));
    }

    public Member createMember2() {
        String name = "사용자";
        String email = "member1@email.com";
        String password = "password";
        return memberRepository.save(Member.createMember(name, email, password));
    }

    public Member createAdmin() {
        String name = "관리자";
        String email = "admin@email.com";
        String password = "passwordOfAdmin";
        return memberRepository.save(Member.createAdmin(name, email, password));
    }
}
