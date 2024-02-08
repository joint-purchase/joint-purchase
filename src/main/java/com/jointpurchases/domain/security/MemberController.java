package com.jointpurchases.domain.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/signup")
    public String signUp(MemberCreateForm memberCreateForm){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "signup";
        }
        if(!memberCreateForm.getPassword().equals(memberCreateForm.getCheckPassword())){
            bindingResult.rejectValue("checkPassword","passwordIncorrect","2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }
        if (memberService.isUsernameAlreadyInUse(memberCreateForm.getName())) {
            bindingResult.rejectValue("name", "usernameAlreadyInUse", "이미 등록된 사용자입니다.");
            return "signup";
        }

        try {
            memberService.create(memberCreateForm);
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }
}
