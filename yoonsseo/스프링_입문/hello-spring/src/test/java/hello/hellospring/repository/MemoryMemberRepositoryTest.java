package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    //callback 메소드, 메소드 실행이 끝날 때마다 동작
    public void afterEach() {
        repository.clearStore();
        //test가 끝날 때마다 리포지토리를 깔끔하게 지워주는 코드 필요!
    }
    @Test
    public void save() {
        Member member = new Member();
        member.setName("SPRING");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //findById의 반환 타입 Optional에서 값을 꺼낼 때 get 이용
        //get을 이용하면 Optional<Member> 타입에서 Optional을 까서 꺼낼 수 있음
        //System.out.println(result);
        //hello.hellospring.domain.Member@3bd82cf5

        //System.out.println("result = " + (result == member)); //result = true
        //Assertions.assertEquals(member, result); //따로 출력값 없음
        //기대한 값 member에 실제값 result 같은면 test 성공 다르면 fail
        assertThat(member).isEqualTo(result);
        //요즘에는 Assertions.assertEquals보다 assertThat 많이 씀
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
