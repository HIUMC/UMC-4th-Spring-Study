package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
    /**
     * Spring Data JPA 가 아닌, 내가 직접 구현한 기능을 사용하고 싶은 경우
     */
}
