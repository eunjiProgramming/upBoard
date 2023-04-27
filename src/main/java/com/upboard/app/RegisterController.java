package com.upboard.app;

import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class RegisterController {

    @InitBinder
    public void toDate(WebDataBinder binder) {
        // ConversionService conversionService = binder.getConversionService();
        // System.out.println("conversionService = " + conversionService);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor("#"));
        // binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
        // binder.addValidators(new UserValidator());
        List<Validator> validatorList = binder.getValidators();
        System.out.println("validatorList = " + validatorList);
    }

    @RequestMapping(value = "/register/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String register() {

        return "registerForm";
    }

    // @RequestMapping(value="/register/save", method=RequestMethod.POST)
    @PostMapping("/register/save")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result = " + result);
        System.out.println("user = " + user);

        // 수동 검증 - Validator를 직접 생성하고, validate()를 직접 호출
        // UserValidator userValidator = new UserValidator();
        // userValidator.validate(user, result); // BindingResult는 Errors의 자손

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
