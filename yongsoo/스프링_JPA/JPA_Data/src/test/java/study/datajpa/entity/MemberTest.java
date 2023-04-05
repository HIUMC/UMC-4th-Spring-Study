package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //em.flush(); // DB 에 강제로 insert 쿼리를 날린다.
        //em.clear(); // DB 에 쿼리를 날린 후 JPA 의 영속성 컨텍스트 캐시를 다 날려버린다.

        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }

    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        //given
        Member member1 = new Member("member1");
        memberRepository.save(member1); // @PrePersist 발생

        Thread.sleep(1000);
        member1.setUsername("member2");
        em.flush(); // @PreUpdate 발생
        em.clear();
        //when
        Member findMember = memberRepository.findById(member1.getId()).get();

        //then
        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getUpdatedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
    }

}