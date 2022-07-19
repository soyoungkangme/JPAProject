package com.fastcampus;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fastcampus.domain.Member;
import com.fastcampus.persistence.MemberRepository;


// 


@SpringBootTest
@Transactional // LazyInitializationException (테스트 실패) -> select 한 엔티티가 영속성 컨텍스트 내에 존재하지 않기 때문에 발생
				// => 지연로딩으로 되어있는 연관관계를 즉시로딩으로 변경하여 한번에 가져오거나 Test 메서드에 @Transactional 줘서 트랜잭션내에 존재하도록 해야함
class QueryMethodTest {

	@Autowired
	private MemberRepository memberRepository;

	// @BeforeEach // 모든 테스트 메서드 직전 동작 (한번만 실행)
	public void dataInsert() {
		for (int i = 1; i <= 200; i++) { // 검색 위해 데이터 200건 등록
			Member member = new Member();
			member.setName("테스터" + i);
			member.setCity("서울" + i);
			memberRepository.save(member); // insert
		}
	}

	// @Test
	void queryMethodTest() {
		Pageable pageable = PageRequest.of(0, 5); // page(몇페이지 볼거냐), size(몇개씩 볼거냐) (페이지 정보 담은 객체 생성)

		// List<Member> memberList =
		// memberRepository.findByNameContainingOrderByIdDesc("스터10");
		Page<Member> pageInfo = memberRepository.findByNameContainingOrderByIdDesc("스터10", pageable);

		// 페이지 타입의 객체 (검색결과 + 페이지 정보) 에서 검색결과(List) 추출 = getContent()
		System.out.println("[검색된 회원 목록]");
		for (Member page : pageInfo.getContent()) {
			System.out.println("--->" + page.toString());
		}
		System.out.println("검색된 데이터의 수 : " + pageInfo.getTotalElements());

		// 페이지 정보 추출
		System.out.println("전체 페이지 수 : " + pageInfo.getTotalPages());
		System.out.println("한 페이지에 출력되는 데이터의 수 : " + pageInfo.getSize());
		System.out.println("현재 페이지가 첫번째 페이지인가? : " + pageInfo.isFirst());
		System.out.println("현재 페이지가 마지막 페이지인가? : " + pageInfo.isLast());

	}

	@Test
	void queryMethodTest1() {
		List<Member> memberList = memberRepository.getMemberList("09");    // 09 = keyword
		System.out.println("[검색된 회원 목록]");
		for (Member page : memberList) {
			System.out.println("--->" + page.toString());
		}

	}
}
