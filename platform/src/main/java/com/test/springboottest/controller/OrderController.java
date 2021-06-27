package com.test.springboottest.controller;

import com.test.springboottest.entity.Good;
import com.test.springboottest.entity.Ordermessage;
import com.test.springboottest.entity.Orders;
import com.test.springboottest.entity.User;
import com.test.springboottest.repository.GoodRepository;
import com.test.springboottest.repository.OrderRepository;
import com.test.springboottest.repository.ShoppingcartRepository;
import com.test.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingcartRepository shoppingcartRepository;
    @PostMapping("/addorder")//增加订单
    public boolean addorder(@RequestBody Good good){
        if(good.getOwnername().equals((String)UserController.mysession.getAttribute("username"))){
            return false;
        }
        Orders order=new Orders();
        order.setGoodID(good.getGoodID());
        order.setBuyerID((Integer) UserController.mysession.getAttribute("userID"));
        User user=userRepository.findByUsername(good.getOwnername());
        order.setSellerID(user.getUserID());
        order.setIsshow(0);
        order.setOrderstate("进行中");
        //Good good=goodRepository.findGoodByOwnername((String)UserController.mysession.getAttribute("username"));
        //good.setState("完成");
        //goodRepository.save(good);
        shoppingcartRepository.delete(shoppingcartRepository.findByGoodIDAndUserID(good.getGoodID(),(Integer) UserController.mysession.getAttribute("userID")));
        orderRepository.save(order);
        return true;
    }
    @GetMapping("/findorder")//查找订单
    public List<Ordermessage> findorder(){
        List<Ordermessage> ordermessageList=new ArrayList<Ordermessage>();
        int userID= (int) UserController.mysession.getAttribute("userID");
        List<Orders> mylist=orderRepository.findByBuyerIDOrSellerID(userID,userID);
        //List<Orders> mylist=new ArrayList<Orders>();
        for(Orders orders:mylist){
            if(!orders.getOrderstate().equals("已取消")||orders.getIsshow()==0){
                Ordermessage ordermessage=new Ordermessage();
                ordermessage.setOrderID(orders.getOrderID());
                Good good=goodRepository.findByGoodID(orders.getGoodID());
                User buyer=userRepository.findByUserID(orders.getBuyerID());
                User seller=userRepository.findByUserID(orders.getSellerID());
                ordermessage.setGoodname(good.getGoodname());
                ordermessage.setPrice(good.getPrice());
                ordermessage.setDescription(good.getDescription());
                ordermessage.setBuyer(buyer.getUsername());
                ordermessage.setSeller(seller.getUsername());
                ordermessage.setOrderstate(orders.getOrderstate());
                ordermessage.setComment(orders.getComment());
                ordermessage.setIsshow(orders.getIsshow());
                ordermessageList.add(ordermessage);
            }
        }
        //List<Orders> mylist=orderRepository.findBySellerAndStateNotOrBuyerAndStateNot("xixi","完成","xixi","完成");
        //for(Orders order:mylist){
        //    System.out.println(order.getId());
       // }
        return ordermessageList;
        //return orderRepository.findBySellerAndStateNotOrBuyerAndStateNot("xixi","完成","xixi","完成");
    }
    @PostMapping("/cancel")//取消订单
    public boolean cancel(@RequestBody Orders order){
        Orders order1=orderRepository.findByOrderID(order.getOrderID());
        order.setGoodID(order1.getGoodID());
        order.setBuyerID(order1.getBuyerID());
        order.setSellerID(order1.getSellerID());
        order.setOrderstate("已取消");
        order.setIsshow(0);
        orderRepository.save(order);
        Good good=goodRepository.findByGoodID(order.getGoodID());
        good.setGoodstate("已发布");
        goodRepository.save(good);
        return true;
    }
    @PostMapping("/delete")//删除订单
    public boolean delete(@RequestBody Orders order){
        orderRepository.delete(order);
        return true;
    }
    @PostMapping("/confirm")//确认订单
    public boolean confirm(@RequestBody Orders order){
        Orders order1=orderRepository.findByOrderID(order.getOrderID());
        //System.out.println(Integer.parseInt(UserController.mysession.getAttribute("userID").toString()));
        //System.out.println(order.getBuyerID());
        if(order1.getOrderstate().equals("买家待确认")){
            //Good good=goodRepository.findByGoodnameAndOwnername(order.getGoodname(),order.getOwnername());
            //good.setState("交易完成");
            //goodRepository.save(good);
            if(order1.getBuyerID()==(int)UserController.mysession.getAttribute("userID")) {
                order1.setOrderstate("已完成");
                Good good = goodRepository.findByGoodID(order1.getGoodID());
                good.setGoodstate("已出售");
                goodRepository.save(good);
            }
            else {
                return false;
            }
        }
        else if(order1.getOrderstate().equals("卖家待确认")){
            //Good good=goodRepository.findByGoodnameAndOwnername(order.getGoodname(),order.getOwnername());
            //good.setState("交易完成");
            //goodRepository.save(good);
            if(order1.getSellerID()==(int)UserController.mysession.getAttribute("userID")) {
                order1.setOrderstate("已完成");
                Good good = goodRepository.findByGoodID(order1.getGoodID());
                good.setGoodstate("已出售");
                goodRepository.save(good);
            }
            else {
                return false;
            }
        }
        else if(order1.getOrderstate().equals("进行中")){
            if((int)UserController.mysession.getAttribute("userID")==order1.getBuyerID()){
                //System.out.println("---------------------------------------");
                order1.setOrderstate("卖家待确认");
            }
            else if((int)UserController.mysession.getAttribute("userID")==order1.getSellerID()){
                order1.setOrderstate("买家待确认");
            }
        }
        //System.out.println("---------------------------------------"+order1.toString());
        orderRepository.save(order1);
        return true;
    }
    @PostMapping("/returngood")//退货
    public boolean returngood(@RequestBody Orders oldorder){
        Orders temporder=new Orders();
        temporder=orderRepository.findByOrderID(oldorder.getOrderID());
        temporder.setOrderstate("已退货");
        orderRepository.save(temporder);
        Orders neworder=new Orders();
        neworder.setBuyerID(temporder.getSellerID());
        neworder.setSellerID(temporder.getBuyerID());
        neworder.setGoodID(temporder.getGoodID());
        neworder.setOrderstate("进行中");
        neworder.setIsshow(0);
        orderRepository.save(neworder);
        return true;
    }
    /*@PostMapping("/produce")
    public boolean produceorder(@RequestBody Order order){
        order.setBuyername((String)UserController.mysession.getAttribute("name"));
        System.out.println(order.toString());
        orderRepository.save(order);
        return true;
    }
    @GetMapping("/findmine")
    public List<Order> findmine(){
        String name=UserController.mysession.getAttribute("name").toString();
        return orderRepository.findByStateAndOwnernameOrBuyernameAndState("进行",name,name,"进行");
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Order orders){
        if(orders.getState().equals("完成")){
            Good good=goodRepository.findByGoodnameAndOwnername(orders.getGoodname(),orders.getOwnername());
            good.setState("交易完成");
            goodRepository.save(good);
            Chatroom history=new Chatroom();
            //history.setGoodname(orders.getGoodname());
            //history.setOwnername(orders.getOwnername());
            //history.setBuyername(orders.getBuyername());
            historyRepository.save(history);
        }
        orderRepository.save(orders);
        return true;
    }
    @PostMapping("/delete")
    public boolean delete(@RequestBody Order orders){
        orders.setState("取消");
        Good good=goodRepository.findByGoodnameAndOwnername(orders.getGoodname(),orders.getOwnername());
        good.setState("发布");
        goodRepository.save(good);
        orderRepository.save(orders);
        return true;
    }*/
}
