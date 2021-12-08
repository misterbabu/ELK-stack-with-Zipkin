package com.student.os.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.os.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
