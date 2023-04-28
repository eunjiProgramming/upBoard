package com.upboard.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @InitBinder
    public void toDate(WebDataBinder binder) {
        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
    }

//    @RequestMapping(value="/register/add", method= {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/register/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/register/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result = " + result);
        System.out.println("user = " + user);


        // User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함
        if(result.hasErrors()) {
            return "registerForm";
        }

        /*// 1. 유효성 검사
        if (!isValid(user)) {
            String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
            m.addAttribute("msg", msg);
            return "forward:/register/add"; // 신규회원 가입화면으로 이동(redirect)
        }*/

        // 2. DB에 신규회원 정보를 저장
        return "registerInfo";
    }

    private boolean isValid(User user) {
        return true;
    }
}