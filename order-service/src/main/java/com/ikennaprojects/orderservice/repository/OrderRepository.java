package com.ikennaprojects.orderservice.repository;

import com.ikennaprojects.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
