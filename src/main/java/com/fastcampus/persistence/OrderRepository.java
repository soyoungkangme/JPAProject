package com.fastcampus.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {    // 엔티티 
	
	
}
