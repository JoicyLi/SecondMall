package com.test.springboottest.service;

import com.test.springboottest.entity.Shoppingcart;
import com.test.springboottest.repository.ShoppingcartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionService {
    @Autowired
    private ShoppingcartRepository permissionRepository;
    public boolean edit(Shoppingcart permission){
        permissionRepository.save(permission);
        return true;
    }
    public boolean delete(Shoppingcart permission){
        permissionRepository.delete(permission);
        return true;
    }
    public List<Shoppingcart> findall(){
        return permissionRepository.findAll();
    }
}
