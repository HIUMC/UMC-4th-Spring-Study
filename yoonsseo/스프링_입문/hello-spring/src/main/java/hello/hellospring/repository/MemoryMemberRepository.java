package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    //Map은 interface이기 때문에 선언만 가능, 객체 생성 불가능
    //save할 때 저장할 공간 필요 memory니까
    //Map에서 키 id의 Long, 값 Member
    //실무에서는 동시성 문제가 있을 수 있어서,
    //공유되는 변수일 경우 concurrenthashmap(?)을 써야 하는데
    //예제니까 단순하게 hashmap 사용

    private static long sequence = 0L;
    //sequence는 0,1,2... 단순하게 키 값을 생성하는 얘라고 생각
    //실무에서는 long보다 동시성 문제를 고려해 atomiclong? 해야하는데 그냥 예제니까..

    @Override
    public Member save(Member member) {
        //save하기 전 이름은 넘어온 상태라고 생각
        //id는 시스템이 정하고, 이름은 고객이 입력
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //결과가 없으면? null이면?
        //Optional.ofNullable로 감싸면 client에서 어떤 처리 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //루프로 스트림 내 요소에서 필터링
                .filter(member -> member.getName().equals(name))
                //member.getName()이 파라미터로 넘어온 name과 같은지 확인
                .findAny();
                //하나라도 있으면 반환하고 없으면 optional의 null로
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    //List도 interface 부모이기 때문에 자식인 ArrayList로 객체 생성
    //저장은 Map으로 되어있는데 반환은 list로 되어 있음
    //자바 실무에서는 list 많이 씀

    public void clearStore() {
        store.clear();
    }
}
