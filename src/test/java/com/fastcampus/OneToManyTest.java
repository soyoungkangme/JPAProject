package com.fastcampus;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fastcampus.domain.Member;
import com.fastcampus.domain.Order;
import com.fastcampus.persistence.MemberRepository;



// N:1 양방향 매핑 (양쪽에서 검색 가능) 
// 테이블은 N:1 만 있지만 엔티티는 1:N 가능 

// 조인된 테이블간의 Repository 메서드 테스트 (findById())
// Member로 Order 조회 


@SpringBootTest
@Transactional    // select 시작 전후에 동작 for Lazy 로딩 동작 (등록, 수정, 삭제는 필요 없음) 
class OneToManyTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	// 멤버 아이디(일련번호)로 주문내역 검색 
	@Test
	void dataSelect() {
		Optional<Member> findMember = memberRepository.findById(1);    // 회원정보 select 
		if(findMember.isPresent()) {
			Member member = findMember.get();    // Member 타입으로 형변환 
			System.out.println(member.getId() + "번 회원의 상세 정보");
			System.out.println("회원 이름 : " + member.getName());    
			System.out.println("거주 도시 : " + member.getCity());    // 여기까지는 Member만 select 
			
			
			// select한 Member로 Order 조회 (양방향매핑, OneToMany) 
			System.out.println("[주문 목록]");
			for (Order order : member.getOrders()) {    // 여기서 조인 (디폴트 LAZY) 후 Order select     // 콜렉션 반환 
				// System.out.println("--->" + order.toString());    // 순환 참조 에러 
				System.out.println("--->" + order.getId() + ", " + order.getStatus()); 
			}  
		}
	}


}
