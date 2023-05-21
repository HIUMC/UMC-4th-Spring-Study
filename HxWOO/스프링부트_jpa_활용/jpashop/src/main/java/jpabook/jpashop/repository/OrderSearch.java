package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    public String memberName; //회원 이름
    public OrderStatus orderStatus; //주문 상태 ORDER, CANCEL

}
