package com.test.springboottest.controller;

import com.test.springboottest.entity.Chatroom;
import com.test.springboottest.entity.Chatuse;
import com.test.springboottest.entity.User;
import com.test.springboottest.repository.ChatroomRepository;
import com.test.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatroom")
public class ChatroomController {
    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/find")
    public List<Chatuse> find(@RequestBody User user){
        int chat=userRepository.findByUsername(user.getUsername()).getUserID();
        int userID= (int) UserController.mysession.getAttribute("userID");
        List<Chatuse> chatuseList=new ArrayList<Chatuse>();
        List<Chatroom> chatroomList=new ArrayList<Chatroom>();
        chatroomList=chatroomRepository.findBySenderIDAndGetterIDOrSenderIDAndGetterID(chat,userID,userID,chat);
        for(Chatroom chatroom:chatroomList){
            Chatuse chatuse=new Chatuse();
            chatuse.setChatID(chatroom.getChatID());
            String getter=userRepository.findByUserID(chatroom.getGetterID()).getUsername();
            String sender=userRepository.findByUserID(chatroom.getSenderID()).getUsername();
            chatuse.setGetter(getter);
            chatuse.setSender(sender);
            chatuse.setContent(chatroom.getContent());
            chatuseList.add(chatuse);
        }
        return chatuseList;
    }
    /*@PostMapping("/delete")
    public boolean delete(@RequestBody Chatroom chatroom){
        chatroomRepository.delete(chatroom);
        return true;
    }*/
    @PostMapping("/addchat")
    public boolean addchat(@RequestBody Chatuse chatuse){
        Chatroom chatroom=new Chatroom();
        chatroom.setSenderID((Integer) UserController.mysession.getAttribute("userID"));
        int getterid=userRepository.findByUsername(chatuse.getGetter()).getUserID();
        chatroom.setGetterID(getterid);
        //System.out.println(chatuse.getContent());
        chatroom.setContent(chatuse.getContent());
        chatroom.setIsshow(0);
        chatroomRepository.save(chatroom);
        return true;
    }
    @GetMapping("/users")
    public List<String> users(){//@RequestBody Chatroom chatroom
        int userID= (int) UserController.mysession.getAttribute("userID");
        //System.out.println(UserController.mysession.getAttribute("userID"));
        List<Chatroom> chatroomList=chatroomRepository.findBySenderIDOrGetterID(userID,userID);
        List<Integer> userids=new ArrayList<Integer>();
        List<String> users=new ArrayList<String>();
        for(Chatroom chatroom1:chatroomList){
            //System.out.println(chatroom1.toString());
            //System.out.println(chatroom1.toString());
            if(chatroom1.getGetterID()==userID){
               if(!userids.contains(chatroom1.getSenderID())&&chatroom1.getSenderID()!=userID){
                   userids.add(chatroom1.getSenderID());
               }

            }
            else if(chatroom1.getSenderID()==userID){
                if(!userids.contains(chatroom1.getGetterID())&&chatroom1.getGetterID()!=userID){
                    userids.add(chatroom1.getGetterID());
                }
            }
            /*if((!(chatroom1.getSenderID()==userID)||!(chatroom1.getGetterID()==userID))){
                if(!userids.contains(chatroom1.getGetterID())){
                    userids.add(chatroom1.getGetterID());
                }
                else if(!userids.contains(chatroom1.getSenderID())){
                    userids.add(chatroom1.getGetterID());
                }
            }*/
            /*if(!userids.contains(chatroom1.getGetterID())){
                if(!(chatroom1.getGetterID()==userID)){
                    userids.add(chatroom1.getGetterID());
                }
            }
            else if(!userids.contains(chatroom1.getSenderID())){
                if(!(chatroom1.getSenderID()==userID)){
                    userids.add(chatroom1.getSenderID());
                }
            }*/
        }
        for(int userid:userids){
            String username=userRepository.findByUserID(userid).getUsername();
            users.add(username);
        }
        //System.out.println(users.get(0));
        return users;
    }
    /*@GetMapping("/mine")
    public List<Chatroom> mine(){
     return historyRepository.findByOwnernameOrBuyername(UserController.mysession.getAttribute("name").toString(),UserController.mysession.getAttribute("name").toString());
    }
    @PostMapping("/delete")
    public boolean delete(@RequestBody Chatroom history){
        historyRepository.delete(history);
        return true;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Chatroom history){
        historyRepository.save(history);
        return true;
    }*/
}
