package com.test.springboottest.controller;

import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.Shoppingcart;
import com.test.springboottest.repository.GoodRepository;
import com.test.springboottest.repository.ShoppingcartRepository;
import com.test.springboottest.repository.UserRepository;
import com.test.springboottest.service.GoodService;
import com.test.springboottest.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingcartController {
    @Autowired
    private ShoppingcartRepository shoppingcartRepository;
    @Autowired
    private GoodRepository goodRepository;
    @PostMapping("/addshoppingcart")//添加购物车 5|c7100L
    public boolean addshoppingcart(@RequestBody Good good){
        if(good.getOwnername().equals((String)UserController.mysession.getAttribute("username"))){
            return false;
        }
        if(shoppingcartRepository.findByGoodIDAndUserID(good.getGoodID(),(Integer) UserController.mysession.getAttribute("userID"))==null) {
            Shoppingcart shoppingcart = new Shoppingcart();
            //System.out.println((String)UserController.mysession.getAttribute("userID"));
            shoppingcart.setUserID((Integer) UserController.mysession.getAttribute("userID"));
            shoppingcart.setGoodID(good.getGoodID());
            shoppingcartRepository.save(shoppingcart);
            return true;
        }
        else {
            return false;
        }
    }
    @PostMapping("/delete")//删除购物车
    public boolean delete(@RequestBody Good good){
        Shoppingcart shoppingcart=new Shoppingcart();
        shoppingcart.setUserID((Integer) UserController.mysession.getAttribute("userID"));
        shoppingcart.setGoodID(good.getGoodID());
        shoppingcartRepository.delete(shoppingcartRepository.findByGoodIDAndUserID(good.getGoodID(),(Integer) UserController.mysession.getAttribute("userID")));
        return true;
    }
   @GetMapping("/find")//查找购物车
    public List<Good> find(){
        List<Shoppingcart> shoppingcartList=shoppingcartRepository.findByUserID((Integer) UserController.mysession.getAttribute("userID"));
        //System.out.println( (Integer) UserController.mysession.getAttribute("userID"));
        List<Good> goodList=new ArrayList<Good>();
        for(Shoppingcart shoppingcart:shoppingcartList){
            Good good=goodRepository.findByGoodID(shoppingcart.getGoodID());
            goodList.add(good);
            /*if(good.getGoodstate().equals("已发布")) {
                goodList.add(good);
            }*/

        }
        return goodList;
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
