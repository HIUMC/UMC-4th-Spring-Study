package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        /**
         * 어차피 id 는 PK 값에 해당하기 때문에 이럴경우 도메인 클래스 컨버터를 사용할 수 있다.
         */
        Member findMember = memberRepository.findById(id).get();
        return findMember.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
       return member.getUsername();
        /**
         * Http 파라미터로 넘어온 엔티티의 id 로 엔티티 객체를 찾아서 바인딩한다.
         * 도메인 클래스 컨버터로 엔티티를 받을 경우 반드시 조회용으로 사용해야한다.
         */
    }

    @PostConstruct
    public void init() {
        for(int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }


    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDto::new);
        /**
         * 스프링 데이터 JPA 가 제공하는 findAll() 메서드의 경우 Pageable 메서드를 넘겨주면 된다.
         * map 메서드를 통해 Dto 로 변환하여 return 한다. Dto 는 엔티티를 봐도 상관없다.
         * ?page=x&size=x&sort=id,desc
         */
    }

}
