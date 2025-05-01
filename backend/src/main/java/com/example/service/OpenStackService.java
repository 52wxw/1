package com.example.service;

public interface OpenStackService {

    String getConsoleUrl();

    boolean validateToken(String token);
}