package com.test.springboottest.repository;

import com.test.springboottest.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom,Integer> {
    List<Chatroom> findBySenderIDOrGetterID(int senderID, int sellerID);
    List<Chatroom> findBySenderIDAndGetterIDOrSenderIDAndGetterID(int senderid,int getterid,int getterid1,int senderid1);
}
