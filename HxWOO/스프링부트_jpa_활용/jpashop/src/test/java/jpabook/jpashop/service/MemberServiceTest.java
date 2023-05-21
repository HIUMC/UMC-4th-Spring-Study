package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @org.junit.jupiter.api.Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertThat(member.getId()).isEqualTo(saveId);
    }

    @org.junit.jupiter.api.Test
    public void 회원_중복_예제() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        //then
        assertThrows(IllegalStateException.class,
                ()->memberService.join(member2));
    }
}
//junit5으로 해서 이렇게 해야했음