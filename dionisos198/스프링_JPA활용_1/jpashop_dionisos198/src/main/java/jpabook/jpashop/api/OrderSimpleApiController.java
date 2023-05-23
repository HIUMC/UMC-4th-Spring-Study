package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all=orderRepository.findAllByString(new OrderSearch());
        for(Order order:all){
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders=orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result=orders.stream().map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders=orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result=orders.stream().map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v31/simple-orders")
    public List<SimpleOrderDto> ordersV31(){
        List<Order> orders=orderRepository.findAllWithMemberDeliveryWithOutFetch();
        List<SimpleOrderDto> result=orders.stream().map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    static class SimpleOrderDto{
        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        SimpleOrderDto(Order order){
            this.id=order.getId();
            this.name=order.getMember().getName();
            this.orderDate=order.getOrderDate();
            this.orderStatus=order.getStatus();
            this.address=order.getDelivery().getAddress();
        }

    }
}
