package com.example.emailover.service;

import com.example.emailover.model.email;

import java.text.ParseException;

public interface emailService {
    boolean emailgo(email em) throws ParseException;
    int getI();
    boolean stop();
}
