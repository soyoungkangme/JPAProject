package com.fastcampus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fastcampus.persistence.MemberRepository;


// 1번 회원이 5건의 주문 내역이 있는 상태에서 1번 회원 삭제 불가, 1번 회원의 주문내역 5건 전부 삭제 해야 1번 회원 삭제 가능함 


@SpringBootTest
// @Transactional  
class CascadeTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	void dataDelete() {
		memberRepository.deleteById(1);    // CascadeType.REMOVE 설정하면 에러 안남 (회원 삭제 전, 회원이 작성한 댓글과 포스트 먼저 삭제 자동)
	}

}

// 회원이 작성한 포스팅, 포스팅에 달린 댓글 한번에 삭제 가능 