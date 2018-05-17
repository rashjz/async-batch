package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    private BigDecimal userID;
    private String username;
    private boolean admin;
    private String status;
}