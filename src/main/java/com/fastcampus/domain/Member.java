package com.fastcampus.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;


// One 


@Entity    // 필수 
@Data    // lomBok
public class Member {
	
	@Id    // 필수 (primary key 지정) 
	@GeneratedValue(strategy = GenerationType.IDENTITY)    // 1씩 자동 증가 (int 타입만 가능) 
	@Column(name = "MEMBER_ID")
	private int id;    // 로그인에 사용하지 않는 회원의 일련번호 
	
	private String name;
	
	private String city;
	
	// 양방향 매핑 (orders로 Member 조회) 
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)   
	private List<Order> orders = new ArrayList<Order>();    // 비어있는 배열 할당 
}

/*
fetch = 엔티티 로딩 (LAZY <-> EAGER), 디폴트 LAZY 
mappedBy = Order 엔티티의 참조변수 이름 
cascadeType.REMOVE = 멤버가 삭제할때 관련된 주문 목록이 먼저 자동으로 삭제 (내가 직접 삭제 안해도됨)  
           .PERSIST = 회원 등록할때 주문 목록도 같이 insert (사용 잘안함) 
*/