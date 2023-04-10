package com.together.web;

import com.together.config.auth.PrincipalDetails;
import com.together.domain.user.User;
import com.together.domain.user.UserRepository;
import com.together.service.UserService;
import com.together.web.dto.UserDto;
import com.together.web.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user/{pageUserid}")
    public String profile(@PathVariable int pageUserid, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UserProfileDto dto = userService.회원프로필(pageUserid, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        System.out.println("세션 정보 : " + principalDetails.getUser());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//        System.out.println("직접 찾은 세션 정보: " + mPrincipalDetails.getUser());
        return "user/update";
    }

    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("Id", id);
        return "user/delete-confirm";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable int id, @RequestParam String confirm, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id, password); // 회원 탈퇴 처리
            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
            return "redirect:/auth/signin";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다."); // 에러 메시지 설정
            return "redirect:/user/" + id + "/delete";
        }
    }

    @GetMapping({"/members/search"})
    public String searchLink() {
        return "members/search";
    }

    @GetMapping("members/memberList")
    public String list (@RequestParam(value = "keyword", required = false) String keyword, Model model) { //회원 조회,검색
        List<UserDto> members = userService.findMember(keyword);
        model.addAttribute("members",members);
        return "/members/memberList";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 유저가 없습니다."));
    }
}
