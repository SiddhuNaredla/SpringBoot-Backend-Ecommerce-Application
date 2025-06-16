package com.example.ecomproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
