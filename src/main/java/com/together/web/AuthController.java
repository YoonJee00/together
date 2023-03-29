package com.together.web;

import com.together.domain.user.User;
import com.together.handler.ex.CustomValidationException;
import com.together.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.together.web.dto.SignupDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller //1.IoC에 등록이 됐다는 의미 2.파일을 리턴하는 컨트롤러
public class AuthController {


    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // form으로 데이터가 날아오면 데이터 형식이 key=value 형식으로 데이터가 들어온다. (x-www.form-urlencoded 방식)

        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }
//        log.info(signupDto.toString());
        // User 오브젝트에 SignupDto 데이터를 넣어야함
        User user = signupDto.toEntity();
//        log.info(user.toString());
        User userEntity = authService.회원가입(user);
//        System.out.println(userEntity);
        return "auth/signin";
    }

}