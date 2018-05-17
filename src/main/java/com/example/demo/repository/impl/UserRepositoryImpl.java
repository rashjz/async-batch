package com.example.demo.repository.impl;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.impl.sp.UserDataSP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDataSP userDataSP;


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().admin(true).username("admin").status("a").build());
        users.add(User.builder().admin(true).username("user-*x").status("a").build());
        return users;
    }

    @Override
    public List<User> getAllUsersByRmCode(String rmCode)  {
        return null;
    }
}
