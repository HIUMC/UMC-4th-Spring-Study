package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //둘다 의존하고 있어(구현과 추상) DIP를 역전하고 있다 추상화(인터페이스)에 의존해야하는데..
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //생성자로 DI할 때 이렇게 써야함!
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //이것도 OCP위반임

    //private DiscountPolicy discountPolicy;
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member =memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
