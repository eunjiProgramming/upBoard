package com.upboard.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice("com.upboard.app2")
public class GlobalCatcher {
    @ExceptionHandler(Exception.class)
    public String catcher(Exception ex) {
        // m.addAttribute("ex", ex);
        return "error";
    }

    @ExceptionHandler({NullPointerException.class, FileNotFoundException.class})
    public String catcher2(Exception ex) {
        // m.addAttribute("ex", ex);
        return "error";
    }
}
