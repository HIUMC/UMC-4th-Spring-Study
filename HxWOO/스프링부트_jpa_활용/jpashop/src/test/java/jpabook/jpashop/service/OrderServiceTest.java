package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);
        //then
        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER); //상품 주문시 상태는 ORDER
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1); // 주문 상품 종류수 확인
        assertThat(getOrder.getTotalPrice()).isEqualTo(book.getPrice()*orderCount); //총가격 = 수량*상품가격
        assertThat(book.getStockQuantity()).isEqualTo(8); // 주문하면 재고가 줄어듬
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        //when
        int orderCount = 11;

        //then
        Assertions.assertThrows(NotEnoughStockException.class,
                ()->orderService.order(member.getId(), book.getId(), orderCount));
        //주문 재고가 넘쳐서 NotEnoughStockException 에러 발생
        //근데 이 테스트는 remove stock 에 대한 단일 테스트를 하는게 더 좋음
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 3;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);
        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL); // 주문상태 캔슬 확인
        assertThat(book.getStockQuantity()).isEqualTo(10); //재고 원상복귀 확인
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가", "123-123"));
        em.persist(member);
        return member;
    }

}