package com.fastcampus;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fastcampus.domain.Member;
import com.fastcampus.domain.Order;
import com.fastcampus.persistence.MemberRepository;
import com.fastcampus.persistence.OrderRepository;


// 조인된 테이블로 레포지토리의 메서드 테스트 (findById(), save()) -> h2 


@SpringBootTest
@Transactional  // 테스트 메서드 내부에서 lazy 로딩 하기위함 (처음부터 join 하면 (eager) 성능 저하)  // dataInsert() 에서는 주석처리 해야함, 메서드 종료 후 데이터를 원래 상태로 되돌림 
class ManyToOneTests {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	// 주문 아이디로 주문 엔티티 검색 
	// ORDER_ID(PK), STATUS, MEMBER_ID(FK) 컬럼 3개, 데이터 하나도 없는 상태 
	@Test
	void dataSelect() {
		Optional<Order> findOrder = orderRepository.findById(1);
		if(findOrder.isPresent()) {
			Order order = findOrder.get();
			System.out.println(order.getId() + "번 주문 상세 정보");
			System.out.println("주문 상태 : " + order.getStatus());    // 여기까지는 Order만 select (join 안함) 
			System.out.println("주문자 정보 : " + order.getMember().getName());    // Member 사용할때 join, select => fetch = LAZY (FK) => @Transactional 
			System.out.println("주문자 도시 : " + order.getMember().getCity());    
		}
	}
	
	
	// 주문 등록 
	// MEMBER_ID, CITY, NAME 컬럼 3개, 데이터 하나도 없는 상태 
	// @Test   // -> 한번 실행후 주석  // yml의 create/update 설정  
	void contextLoads() {
		Member member1 = new Member();
		member1.setName("둘리");
		member1.setCity("서울");
		memberRepository.save(member1);    // MEMBER_ID = 1 
		
		Member member2 = new Member();
		member2.setName("또치");
		member2.setCity("대전");
		memberRepository.save(member2);    // MEMBER_ID = 2
		
		for (int i =1;  i<=5; i++) {    // 1번 회원이 5번 구매 
			Order order = new Order();
			order.setStatus("배송완료");
			order.setMember(member1);
			orderRepository.save(order);    // ORDER_ID = 1~5, MEMBER_ID = 1 (5개 전부) => N:1 연관 매핑 
		}
	}
	// Member에 insert 2번, Orders에 insert 5번 

}
