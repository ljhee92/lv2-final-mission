package finalmission.application;

import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 등록된 이메일이 아닙니다."));
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[ERROR] 등록된 회원이 아닙니다."));
    }
}
