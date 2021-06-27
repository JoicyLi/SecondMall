package com.test.springboottest.repository;

import com.test.springboottest.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good,Integer> {
    //List<Good> findByState(String state);
   // List<Good> findByOwnername(String ownername);
    //Good findByGoodnameAndOwnername(String goodname,String ownername);
   // Good findGoodByOwnername(String ownername);
    Good findByGoodID(int goodID);
    //List<Good> findByStateAndOwnernameNot(String state,String ownername);
    List<Good> findByGoodstate(String state);
}
