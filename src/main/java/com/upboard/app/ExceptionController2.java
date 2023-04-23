package com.upboard.app;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

//@ResponseStatus(HttpStatus.BAD_REQUEST) // 500 -> 404
class MyException extends RuntimeException {
    MyException(String msg) {
        super(msg);
    }

    MyException() {
        this("");
    }
}

@Controller
public class ExceptionController2 {

    @RequestMapping("/ex3")
    public String main() throws Exception {
        throw new MyException("예외가 발생했습니다.");
    }

    @RequestMapping("/ex4")
    public String main2() throws Exception {
        throw new FileNotFoundException("예외가 발생했습니다.");
    }
}