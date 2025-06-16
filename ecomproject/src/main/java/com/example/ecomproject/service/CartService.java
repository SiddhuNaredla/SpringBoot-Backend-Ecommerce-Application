package com.example.ecomproject.service;

import com.example.ecomproject.dto.AddToCartDto;
import com.example.ecomproject.dto.CartDto;
import com.example.ecomproject.dto.CartItemsDto;
import com.example.ecomproject.exceptions.CartItemNotFoundException;
import com.example.ecomproject.exceptions.ProductNotFoundException;
import com.example.ecomproject.model.Cart;
import com.example.ecomproject.model.Product;
import com.example.ecomproject.model.User;
import com.example.ecomproject.repository.CartRepository;
import com.example.ecomproject.repository.ProductRepository;
import com.example.ecomproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    public String addToCart(AddToCartDto addToCartDto, Principal principal)throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(addToCartDto.getProductId());
        if(product.isEmpty()){
            throw new ProductNotFoundException("product not found"+addToCartDto.getProductId());
        }
        User user=userRepository.findByEmail(principal.getName()).orElse(new User());
        Cart cart=new Cart();
        cart.setUser(user);
        Product product1 = productRepository.findById(addToCartDto.getProductId()).orElse(new Product());
        cart.setProduct(product1);
        cart.setQuantity(addToCartDto.getQuantity());
        cartRepository.save(cart);
        return "item added to cart";
    }

    public CartDto getCartItems(Principal principal) {
        User user=userRepository.findByEmail(principal.getName()).orElse(new User());
        List<Cart> cart=cartRepository.findByUser(user);
        List<CartItemsDto> itemsDtos = new ArrayList<>();
        CartDto items=new CartDto();
        double totalCost=0;
        for(Cart cart1:cart){
            CartItemsDto item = new CartItemsDto();
            item.setId(cart1.getId());
            item.setQuantity(cart1.getQuantity());
            item.setProdcut(cart1.getProduct());
            totalCost+= cart1.getQuantity()*cart1.getProduct().getPrice();
            itemsDtos.add(item);
        }
        items.setCartItemsDtos(itemsDtos);
        items.setTotalCost(totalCost);
        return items;
    }

    public String deleteFromCart(int cartItemId) throws CartItemNotFoundException{
        Optional<Cart> cart = cartRepository.findById(cartItemId);
        if(cart.isEmpty()){
            throw new CartItemNotFoundException("item not found");
        }
        cartRepository.deleteById(cartItemId);
        return "item removed from cart";
    }
}
