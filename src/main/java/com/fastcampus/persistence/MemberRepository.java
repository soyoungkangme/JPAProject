package com.fastcampus.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastcampus.domain.Member;


// @Repository 생략 가능 
public interface MemberRepository extends JpaRepository<Member, Integer> {    // 엔티티, primary key의 타입 
	
	List<Member> findByName(String name);
	// NAME으로 Member 엔티티 검색 
	// 쿼리 메소드 : 복잡한 JPQL을 메소드로 처리할 수 있도록 작성하는 메소드 (SQL을 메소드로 처리 가능?)
	
	List<Member> findByNameContaining(String name);    // select _ from Member where like _
	
	List<Member> findByNameContainingOrCityContaining(String name, String city);    // like 두번 
	
	List<Member> findByNameContainingAndCityContaining(String name, String city);
	
	List<Member> findByNameContainingOrderByIdDesc(String name);    // 아이디 내림차순 정렬
	
	Page<Member> findByNameContainingOrderByIdDesc(String name, Pageable pageable);    // 검색 + 정렬 + 페이징  
	
	// 쿼리메소드 안쓰고 SQL 등록해서도 사용 가능 
	@Query(value = "select member_id, city, name from member where city like '%'||:keyword||'%' order by member_id", nativeQuery= true) 
	List<Member> getMemberList(@Param("keyword") String keyword);    // 파라미터값 세팅하려면 @param
	
}
