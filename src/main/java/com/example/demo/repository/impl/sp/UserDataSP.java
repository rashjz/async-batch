package com.example.demo.repository.impl.sp;

import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;

public class UserDataSP extends StoredProcedure {
    private final String spName;

    public UserDataSP(DataSource dataSource, String name) {
        super(dataSource, name);
        super.setFunction(false);
        this.spName = name;
        this.declareParameters();
    }

    private void declareParameters() {
        compile();
    }
}
