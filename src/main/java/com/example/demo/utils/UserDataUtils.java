package com.example.demo.utils;

import com.example.demo.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataUtils {
    public static Iterable<String> prepareRecord(User data) {
        List<String> record = new ArrayList<>();
        record.add(data.getUsername());
        record.add(data.getStatus());
        record.add(String.valueOf(data.isAdmin()));
        record.add(String.valueOf(data.getUserID()));
        return record;
    }
}
