package com.test.springboottest.controller;

import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.Report;
import com.test.springboottest.entity.Shoppingcart;
import com.test.springboottest.repository.GoodRepository;
import com.test.springboottest.repository.ReportRepository;
import com.test.springboottest.repository.ShoppingcartRepository;
import com.test.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/addreport")
    public boolean addshoppingcart(@RequestBody Report report){
        report.setBuyerID(Integer.parseInt((String)UserController.mysession.getAttribute("userID")));
        reportRepository.save(report);
        return true;
    }
    /*@PostMapping("/delete")
    public boolean delete(@RequestBody Shoppingcart shoppingcart){
        shoppingcart.setUserID(Integer.parseInt((String) UserController.mysession.getAttribute("username")));
        shoppingcartRepository.delete(shoppingcart);
        return true;
    }
   @GetMapping("/find")
    public List<Good> find(){
        List<Shoppingcart> shoppingcartList=shoppingcartRepository.findByUserID(Integer.parseInt((String)UserController.mysession.getAttribute("userID")));
        List<Good> goodList=new ArrayList<Good>();
        for(Shoppingcart shoppingcart:shoppingcartList){
            Good good=goodRepository.findByGoodID(shoppingcart.getGoodID());
            goodList.add(good);
        }
        return goodList;
    }*/
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
