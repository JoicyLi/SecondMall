package com.test.springboottest.controller;

import com.test.springboottest.entity.User;
import com.test.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//5|c7100L
@RestController
@RequestMapping("/user")
public class UserController {
    public static HttpSession mysession;
    //public static List<HttpSession> sessions;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/findall")//管理员和用户
    public List<User> findall(){
        return userRepository.findAll();
    }
    @GetMapping("/finduser")//全部用户
    public List<User> finduser(){
        return userRepository.findByIsmanager(0);
    }
    @PostMapping("/regist")//注册
    public boolean regist(@RequestBody User user){
        if(userRepository.findByUsername(user.getUsername())!=null){
            return false;
        }
        User newuser=new User();
        newuser.setUsername(user.getUsername());
        newuser.setPassword(user.getPassword());
        userRepository.save(user);
        return true;
        }
    @PostMapping("/login")//登录
    public boolean login(@RequestBody User user,HttpSession session){
        String username=user.getUsername();
        String password=user.getPassword();
        //System.out.println(username+password);
        int isManager=user.getIsmanager();
        User result=userRepository.findByUsernameAndPasswordAndIsmanager(username,password,isManager);
        if(result!=null){
            session.setAttribute("username",result.getUsername());
            session.setAttribute("userID",result.getUserID());
            session.setAttribute("password",result.getPassword());
            mysession=session;
            //sessions.add(mysession);
            return true;
        }
        else{
            return false;
        }
    }
    @GetMapping("/logout")//退出登录
    public boolean logout(){
        mysession.invalidate();
        return true;
    }
    @GetMapping("/message")//查询个人信息
    public User message(){
        //System.out.println(mysession.getAttribute("id"));
        int userID=Integer.parseInt(String.valueOf(mysession.getAttribute("userID")));
        //System.out.println(a);
        return userRepository.findByUserID(userID);
    }
    @PostMapping("/edit")//修改个人信息
    public User edit(@RequestBody User user){
        user.setUserID(Integer.parseInt(String.valueOf(mysession.getAttribute("userID"))));
        //user.setUsername(String.valueOf(mysession.getAttribute("username")));
        // user.setPassword(String.valueOf(mysession.getAttribute("password")));
        //userRepository.delete(userRepository.getOne(Integer.parseInt(String.valueOf(mysession.getAttribute("id")))));
        return userRepository.save(user);
    }
    @PostMapping("/delete")//删除用户
    public Boolean delete(@RequestBody User user){
        //user.setUsername(String.valueOf(mysession.getAttribute("username")));
        // user.setPassword(String.valueOf(mysession.getAttribute("password")));
        //userRepository.delete(userRepository.getOne(Integer.parseInt(String.valueOf(mysession.getAttribute("id")))));
        userRepository.deleteById(user.getUserID());
        return true;
    }
    @GetMapping("/cancellation")//注销
    public boolean cancellation(){
        userRepository.delete(userRepository.getOne(Integer.parseInt(String.valueOf(mysession.getAttribute("userID")))));
        mysession.invalidate();
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
