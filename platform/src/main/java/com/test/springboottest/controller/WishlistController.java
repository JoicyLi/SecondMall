package com.test.springboottest.controller;

import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.Shoppingcart;
import com.test.springboottest.entity.Wishlist;
import com.test.springboottest.repository.GoodRepository;
import com.test.springboottest.repository.ShoppingcartRepository;
import com.test.springboottest.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private GoodRepository goodRepository;
    @PostMapping("/addwish")//添加愿望单商品 5|c7100L
    public boolean addwish(@RequestBody Wishlist wishlist){
        int userID= (int) UserController.mysession.getAttribute("userID");
        wishlist.setUserID(userID);
        wishlistRepository.save(wishlist);
        return true;
    }
    @PostMapping("/delete")//删除愿望单商品
    public boolean delete(@RequestBody Wishlist wishlist){
        wishlistRepository.deleteById(wishlist.getWishID());
        return true;
    }
   @GetMapping("/findwish")//查找愿望单
    public List<Wishlist> findwish(){
        int userID= (int) UserController.mysession.getAttribute("userID");
        List<Wishlist> wishlists= wishlistRepository.findByUserID(userID);
        //System.out.println( (Integer) UserController.mysession.getAttribute("userID"));
        return wishlists;
    }
    @PostMapping("/edit")//修改愿望单商品 5|c7100L
    public boolean edit(@RequestBody Wishlist wishlist){
        int userID= (int) UserController.mysession.getAttribute("userID");
        wishlist.setUserID(userID);
        wishlistRepository.save(wishlist);
        return true;
    }
    /*@Autowired
    private UserRepository userRepository;*/
   /* @PostMapping("/delete")
    public boolean delete(@RequestBody Shoppingcart permission){
        //permissionService.edit(permission);
        userRepository.deleteByName(permission.getUsername());
        permissionRepository.delete(permission);
        return true;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Shoppingcart permission){
        permissionRepository.save(permission);
       return true;
    }
    @GetMapping("/findall")
    public List<Shoppingcart> findall(){
        return permissionRepository.findAll();
    }
    @GetMapping("/findmine")
    public Shoppingcart mine(){
        return permissionRepository.findByUsername(UserController.mysession.getAttribute("name").toString());
    }*/
}
