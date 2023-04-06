package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService service;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        service = new MemberService(memberRepository);
    }
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");
        //when
        Long saveId = service.join(member);
        //then
        Member member1 = service.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(member1.getName());
    }

    @Test
    void 회원_중복_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        service.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> service.join(member2));
        //then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void validateDuplicateMember() {
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}