package com.example.shoppingapp.repository;

import java.sql.Connection;

import com.example.shoppingapp.exception.DataException;

public interface DbConnection {
    public Connection getConnection() throws DataException;
}

