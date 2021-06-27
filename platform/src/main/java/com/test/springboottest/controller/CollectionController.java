package com.test.springboottest.controller;

import com.test.springboottest.entity.Collection;
import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.User;
import com.test.springboottest.repository.CollectionRepository;
import com.test.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/findall")//所有收藏
    public List<Collection> findall(){
        return collectionRepository.findAll();
    }
    @PostMapping("/addcollection")//增加收藏
    public boolean addcollection(@RequestBody Good good){
        String username= (String) UserController.mysession.getAttribute("username");
        if(good.getOwnername().equals(username)){
           return false;
        }
        int sellerID=userRepository.findByUsername(good.getOwnername()).getUserID();
        int buyerID= (int) UserController.mysession.getAttribute("userID");
        Collection collection1=collectionRepository.findByBuyerIDAndSellerID(buyerID,sellerID);
        if(collection1!=null){
            return false;
        }
        Collection collection=new Collection();
        collection.setBuyerID(buyerID);
        collection.setSellerID(sellerID);
        collectionRepository.save(collection);
        return true;
    }
    @GetMapping("/find")//用户收藏
    public List<String> find(){
        //System.out.println("开始查询");
        List<Collection> collectionList=collectionRepository.findByBuyerID((Integer) UserController.mysession.getAttribute("userID"));
        List<User> userList=new ArrayList<>();
        List<String> users=new ArrayList<String>();
        for(Collection collection:collectionList){
            //System.out.println(collection.toString());
            User user=userRepository.findByUserID(collection.getSellerID());
            userList.add(user);
            users.add(user.getUsername());
        }
        return users;
    }
    @PostMapping("/delete")//删除
    public boolean delete(@RequestBody User user){
        String seller=user.getUsername();
        int sellerID=userRepository.findByUsername(seller).getUserID();
        int buyerID= (int) UserController.mysession.getAttribute("userID");
        collectionRepository.delete(collectionRepository.findByBuyerIDAndSellerID(buyerID,sellerID));
        //collectionRepository.delete(collection);
        return true;
    }
    /*@PostMapping("/regist")
    public void regist(@RequestBody User user){
       // System.out.println(user.toString());
        Shoppingcart permission=new Shoppingcart();
        permission.setUsername(user.getUsername());
        permission.setLogin("yes");
        permission.setSell("yes");
        permission.setBuy("yes");
        permission.setComment("yes");
        permissionRepository.save(permission);
        userRepository.save(user);
    }
    @PostMapping("/login")
    public int login(@RequestBody Map<String,String> user,HttpSession session){
    String name=user.get("name");
    String password=user.get("password");
    User result=userRepository.findByNameAndPassword(name,password);
    //System.out.println(user);
    session.setAttribute("name",name);
    if(result!=null){
            //System.out.println(result.getIsmanager());
            session.setAttribute("id",result.getId());
            mysession=session;
            System.out.println(mysession.getAttribute("id"));
            return result.getIsmanager();
        }
    else{
        return -1;
        }
    }
    @GetMapping("/logout")
    public boolean logout(){
        mysession.invalidate();
        return true;
    }
    @GetMapping("/message")
    public Optional<User> message(){
        //System.out.println(mysession.getAttribute("id"));
        int a=Integer.parseInt(String.valueOf(mysession.getAttribute("id")));
        System.out.println(a);
        return userRepository.findById(a);
    }
    @PostMapping("/message/putin")
    public boolean changepassword(@RequestBody User user){
        userRepository.delete(userRepository.getOne(Integer.parseInt(String.valueOf(mysession.getAttribute("id")))));
        userRepository.save(user);
        return true;
    }*/
}
