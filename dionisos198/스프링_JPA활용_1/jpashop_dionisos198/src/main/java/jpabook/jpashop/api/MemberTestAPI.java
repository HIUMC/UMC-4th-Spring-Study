package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberTestAPI {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @GetMapping("/api/VJinu/member")
    public List<MemberTestDTO> FindMember(){
        List<Member> findMembers=memberService.findMembers();
        List<MemberTestDTO> result=new ArrayList<>();
        for(int i=0;i< findMembers.size();i++){
            MemberTestDTO tmp=new MemberTestDTO(findMembers.get(i));
            result.add(tmp);
        }
        return result;
    }

    @Data
    static class MemberTestDTO{
        private Long id;
        private String name;
        private Address address;
        //private List<Order> orderList;
        private String itemName;
        public MemberTestDTO(Member member){
            this.id=member.getId();
            this.name=member.getName();
            this.address=member.getAddress();
        }
    }
}
