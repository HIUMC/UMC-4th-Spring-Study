package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //final이 붙은 변수들을 가지고 생성자를 만들어줌 즉 OrderServiceImpl 생성자 자동 생성
public class OrderServiceImpl implements OrderService{



    private final MemberRepository memberRepository; //불변,필수
    private final DiscountPolicy discountPolicy; //불변,필수    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 이렇게 바꾸는 순간 ocp(개방-폐쇄 원칙) 위반 * ocp: 확장에는 열려있으나 변경에는 닫혀있어야 함.


    @Autowired // 생성자가 단 1개 있는 경우는 생략가능, but, 두개 이상일 경우 생략 불가능 !
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    //-> Lombok 의 RequiredArgsConstructor 을 이용하면 필요없어지는 코드

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
