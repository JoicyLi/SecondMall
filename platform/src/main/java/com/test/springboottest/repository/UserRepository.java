package com.test.springboottest.repository;

import com.test.springboottest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsername(String name);
    public List<User> findByIsmanager(int number);
    //User findByUsernameAndPassword(String username,String password);
    public User findByUsernameAndPasswordAndIsmanager(String username,String password,int isManager);
    //boolean deleteByUsername(String name);
    public User findByUserID(int userID);
}
