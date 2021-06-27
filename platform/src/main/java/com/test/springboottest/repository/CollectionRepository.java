package com.test.springboottest.repository;

import com.test.springboottest.entity.Collection;
import com.test.springboottest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection,Integer> {
    List<Collection> findByBuyerID(int buyerID);
    public Collection findByBuyerIDAndSellerID(int BuyerID,int SellerID);
    public Collection deleteByBuyerIDAndSellerID(int buyerID,int sellerID);
}
