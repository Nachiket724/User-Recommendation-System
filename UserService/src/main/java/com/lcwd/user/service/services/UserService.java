package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    //USER OPERATIONS

    //CREATE USER
    Users saveUser(Users user);

    //GET ALL USER
    List<Users> getAllUser();

    //GET SINGLE USER BASED ON USERID
    Users getUser(int userId);

    //DELETE USER
    void deleteUser(int userId);

    //UPDATE USER
    Users updateUser(int userId, Users user);

    //Pagination Support
    Page<Users> getAllUser(Pageable pageable);
}
