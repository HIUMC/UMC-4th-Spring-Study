package hello.core.discount;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 되어야 한다")
    void vip_o(){
        //given
        Member member = new Member(1L,"memberA", Grade.VIP);
        //when
        int discountAmount = rateDiscountPolicy.discount(member,10000);
        //then
        assertThat(discountAmount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_x(){
        //given
        Member member = new Member(1L,"memberA", Grade.BASIC);
        //when
        int discountAmount = rateDiscountPolicy.discount(member,10000);
        //then
        assertThat(discountAmount).isEqualTo(0);
    }

}