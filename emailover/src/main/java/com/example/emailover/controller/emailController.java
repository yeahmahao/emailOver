package com.example.emailover.controller;

import com.example.emailover.model.email;
import com.example.emailover.service.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@CrossOrigin
public class emailController {
    @Autowired
    emailService es;


    @RequestMapping(value = "/goEmail",method = RequestMethod.POST)
    @ResponseBody
    public boolean goEmail(email em) throws ParseException {
        em.setTime(em.getTime() * 1000);
        return es.emailgo(em);
    }

    @RequestMapping(value = "/getI",method = RequestMethod.GET)
    @ResponseBody
    public int getI() {
        return es.getI();
    }

    @RequestMapping(value = "/stop",method = RequestMethod.GET)
    @ResponseBody
    public boolean stop() {
        return es.stop();
    }
}
