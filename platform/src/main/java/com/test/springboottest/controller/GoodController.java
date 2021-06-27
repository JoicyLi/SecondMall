package com.test.springboottest.controller;
import com.test.springboottest.entity.Good;
import com.test.springboottest.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodRepository goodRepository;
    @GetMapping("/findall")//所有
    public List<Good> findall(){
        return goodRepository.findAll();
    }
    @GetMapping("/release")//已发布
    public List<Good> release(){
        return goodRepository.findByGoodstate("已发布");
    }
    @GetMapping("/review")//待审核
    public List<Good> review(){
        return goodRepository.findByGoodstate("待审核");
    }
    @PostMapping("/add")//添加
    public boolean add(@RequestBody Good good){
        //System.out.println(good.getOwnername());
        //good.setGoodname("giao"+good.getGoodname());
        good.setOwnername(UserController.mysession.getAttribute("username").toString());
        good.setGoodstate("待审核");
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/delete")//删除
    public boolean delete(@RequestBody Good good){
        //goodRepository.delete(good);
        good.setOwnername(UserController.mysession.getAttribute("username").toString());
        Good tempgood=goodRepository.findByGoodID(good.getGoodID());
        //good.setGoodstate(tempgood.getGoodstate());
        good.setGoodstate("已下架");
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/edit")//编辑
    public boolean edit(@RequestBody Good good){
        good.setOwnername(UserController.mysession.getAttribute("username").toString());
        Good tempgood=goodRepository.findByGoodID(good.getGoodID());
        good.setGoodstate(tempgood.getGoodstate());
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/changestate")//编辑
    public boolean changestate(@RequestBody Good good){
        Good tempgood=goodRepository.findByGoodID(good.getGoodID());
        good.setGoodstate(good.getGoodstate());
        goodRepository.save(good);
        return true;
    }
    /*@GetMapping("/findsome")
    public List<Good> findsome(){
        //System.out.println(UserController.mysession.getAttribute("name"));
        return goodRepository.findByStateAndOwnernameNot("发布",UserController.mysession.getAttribute("name").toString());
    }
    @GetMapping("/findall")
    public List<Good> findall(){
        return goodRepository.findAll();
    }
    @PostMapping("/state")
    public boolean changestate(@RequestBody Good good){
        //goodRepository.delete(goodRepository.getOne(good.getId()));
        goodRepository.save(good);
        return true;
    }
    @GetMapping("/findmine")
    public List<Good> mygoods(){
        return goodRepository.findByOwnername(UserController.mysession.getAttribute("name").toString());
    }
    @PostMapping("/add")
    public boolean add(@RequestBody Good good){
        good.setOwnername(UserController.mysession.getAttribute("name").toString());
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/delete")
    public boolean delete(@RequestBody Good good){
        goodRepository.delete(good);
        return true;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Good good){
        good.setOwnername(UserController.mysession.getAttribute("name").toString());
        System.out.println(good.toString());
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/order")
    public boolean order(@RequestBody Good good){
        good.setState("锁定");
        goodRepository.save(good);
        return true;
    }*/
}
