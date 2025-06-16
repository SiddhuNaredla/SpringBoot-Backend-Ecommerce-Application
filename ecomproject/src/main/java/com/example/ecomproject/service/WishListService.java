package com.example.ecomproject.service;

import com.example.ecomproject.dto.ProductDto;
import com.example.ecomproject.model.Product;
import com.example.ecomproject.model.User;
import com.example.ecomproject.model.WishList;
import com.example.ecomproject.repository.ProductRepository;
import com.example.ecomproject.repository.UserRepository;
import com.example.ecomproject.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    public String addToWishList(Product product, Principal principal) {
        User user=userRepository.findByEmail(principal.getName()).orElse(new User());
        WishList wishList=new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);
        wishListRepository.save(wishList);
        return "item added into wishlist";
    }

    public List<ProductDto> getWishList(Principal principal) {
        User user=userRepository.findByEmail(principal.getName()).orElse(new User());
        List<WishList> wishList = wishListRepository.findByUser(user);
        List<ProductDto> products=new ArrayList<>();
        for(WishList item:wishList){
            products.add(productService.getProductDto(item.getProduct()));
        }
        return products;
    }

    public String removeFromWishList(Integer productId, Principal principal) {
        User user=userRepository.findByEmail(principal.getName()).orElse(new User());
        Product product=productRepository.findById(productId).orElse(new Product());
        wishListRepository.deleteByUserAndProduct(user,product);
        return "item removed from wishlist";
    }
}
