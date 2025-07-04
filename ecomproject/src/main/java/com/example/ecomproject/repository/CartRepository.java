package com.example.ecomproject.repository;

import com.example.ecomproject.model.Cart;
import com.example.ecomproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findByUser(User user);

    void deleteByUser(User user);
}
