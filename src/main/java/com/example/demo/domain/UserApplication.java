package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserApplication implements Serializable {
    private static final long serialVersionUID = 1905122041950251237L;
    private String applicationID;
    private String departmentID;

}
