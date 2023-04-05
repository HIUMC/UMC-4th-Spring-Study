package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //외부에서 넣어주도록 바꿔줌
    //MemberRepository를 직접 new로 생성하는 것이 아니라 외부에서 넣어주도록

    public Long join(Member member) { //회원가입
            validateDuplicateMember(member); //중복회원 검증
            memberRepository.save(member);
            return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //여기까지의 반환값은 Optional
                .ifPresent(m -> { //ifPresent : null이 아니라 어떤 값이 있으면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() { //전체회원조회
            return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
