package com.upboard.app;

import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String register() {
        return "registerForm";
    }

    // @RequestMapping(value="/register/save", method=RequestMethod.POST)
    @PostMapping("/register/save")
    public String save(User user, Model m) throws Exception {
        // 1. 유효성 검사
        if (!isValid(user)) {
            String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
            m.addAttribute("msg", msg);
            return "forward:/register/add"; // 신규회원 가입화면으로 이동(redirect)
        }

        // 2. DB에 신규회원 정보를 저장
        return "registerInfo";
    }

    private boolean isValid(User user) {
        return false;
    }
}
