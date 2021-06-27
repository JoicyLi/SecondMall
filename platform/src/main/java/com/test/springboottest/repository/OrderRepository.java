package com.test.springboottest.repository;

import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByBuyerIDAndOrderstateOrSellerIDAndOrderstate(String buyerID, String state1, String sellerID, String state2);
    //Good findByBuyerID(int goodID);
    List<Orders> findByBuyerIDOrSellerID(int burerID,int sellID);
    //List<Orders> findBySellerAndStateNotOrBuyerAndStateNot(String buyer, String state1, String seller, String state2);
    //List<Order> findByStateNot(String state);
    Orders findByOrderID(int orderID);
}
