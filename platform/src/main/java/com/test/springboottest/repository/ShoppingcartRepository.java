package com.test.springboottest.repository;

import com.test.springboottest.entity.Shoppingcart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingcartRepository extends JpaRepository<Shoppingcart,Integer> {
    //public List<Shoppingcart> findByUsername(String name);
    public List<Shoppingcart> findByUserID(int userID);
    public Shoppingcart deleteByGoodIDAndAndUserID(int goodID,int userID);
    public Shoppingcart findByGoodIDAndUserID(int goodID,int userID);
}
