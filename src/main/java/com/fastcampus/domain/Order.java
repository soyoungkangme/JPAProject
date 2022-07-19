package com.fastcampus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;



// Many 

// 조인 = 테이블 간 관계를 엔티티로 매핑
// Order:Member = N:1 (한명이 여러개의 주문 가능)
// 참조키(foreign key)는 N이 관리 



@Entity
@Table(name = "ORDERS")    // Order는 SQL 예약어 이므로 
@Data
public class Order {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")    // 변수이름과 다르게 컬럼이름 지정 
	private int id;    // 주문 번호 
	
	private String status;    // 주문 상태 
	
	// 외래키 (Order가 Member 참조)  // member로 Order 조회 
	@ManyToOne(fetch = FetchType.LAZY)    // fetch = 멤버 엔티티를 처음부터 가져오지 말고 사용할때 가져와 (레이지 로딩 처럼) <-> 디폴트 EAGER (처음부터 무조건 join)   
	@JoinColumn(name = "MEMBER_ID")    // 조인에 사용할 컬럼 아이디 (FK) 
	private Member member;   

}
