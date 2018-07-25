package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor@AllArgsConstructor
public class UserApplication implements Serializable {
    private static final long serialVersionUID = 1905122041950251237L;
    private User user;
    private String applicationID;
    private String departmentID;

}
