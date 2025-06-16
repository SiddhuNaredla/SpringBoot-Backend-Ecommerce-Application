package com.example.ecomproject.repository;

import com.example.ecomproject.model.Order;
import com.example.ecomproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUser(User user);
}
