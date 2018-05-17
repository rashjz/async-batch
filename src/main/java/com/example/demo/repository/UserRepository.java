package com.example.demo.repository;

import com.example.demo.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers( ) throws Exception;
    List<User> getAllUsersByRmCode( String rmCode) ;
}
