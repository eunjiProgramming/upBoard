package com.upboard.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserDao userDao;

    final int FAIL = 0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
    }

    //    @RequestMapping(value="/register/add", method= {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result = " + result);
        System.out.println("user = " + user);


        // User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함
        if (!result.hasErrors()) {
            // 2. DB에 신규회원 정보를 저장
            int rowCnt = userDao.insertUser(user);

            if (rowCnt != FAIL) {
                return "registerInfo";
            }
        }
        return "registerForm";
    }


    private boolean isValid(User user) {
        return true;
    }
}