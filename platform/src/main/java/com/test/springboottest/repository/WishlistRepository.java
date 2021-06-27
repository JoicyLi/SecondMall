package com.test.springboottest.repository;

import com.test.springboottest.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Integer> {
    public Wishlist findByWishID(int wishID);
    public List<Wishlist> findByUserID(int userID);
}
