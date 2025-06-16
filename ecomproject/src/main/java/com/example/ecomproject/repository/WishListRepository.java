package com.example.ecomproject.repository;

import com.example.ecomproject.model.Product;
import com.example.ecomproject.model.User;
import com.example.ecomproject.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Integer> {

    List<WishList> findByUser(User user);

    void deleteByUserAndProduct(User user, Product product);
}
