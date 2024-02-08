package com.jointpurchases.domain.security.controller;

import com.jointpurchases.domain.security.MemberCreateForm;
import com.jointpurchases.domain.security.MemberRole;
import com.jointpurchases.domain.security.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("memberCreateForm", new MemberCreateForm());
        Map<String, String> roles = Arrays.stream(MemberRole.values())
                .filter(role -> role != MemberRole.ADMIN) // ADMIN 역할 제외
                .collect(Collectors.toMap(MemberRole::name, MemberRole::getDescription));
        model.addAttribute("roles", roles);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult, Model model) {
        // 회원유형 선택 목록을 재전송
        Map<String, String> roles = Arrays.stream(MemberRole.values())
                .filter(role -> role != MemberRole.ADMIN)
                .collect(Collectors.toMap(MemberRole::name, MemberRole::getDescription));
        model.addAttribute("roles", roles);

        if(bindingResult.hasErrors()){
            return "signup_form";
        }
        if(!memberCreateForm.getPassword().equals(memberCreateForm.getCheckPassword())){
            bindingResult.rejectValue("checkPassword", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        if (memberService.isUsernameAlreadyInUse(memberCreateForm.getName())) {
            bindingResult.rejectValue("name", "usernameAlreadyInUse", "이미 등록된 사용자입니다.");
            return "signup_form";
        }

        try {
            memberService.create(memberCreateForm);
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
