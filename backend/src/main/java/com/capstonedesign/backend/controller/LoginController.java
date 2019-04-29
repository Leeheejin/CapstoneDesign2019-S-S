package com.capstonedesign.backend.controller;

import com.capstonedesign.backend.domain.Account;
import com.capstonedesign.backend.domain.Login;
import com.capstonedesign.backend.exception.IdPasswordNotMatchingException;
import com.capstonedesign.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ModelAndView login(Login login,
                              @CookieValue(value="REMEMBER", required=false) Cookie rememberCookie) throws Exception {

        if(rememberCookie!=null) {
            login.setUserMid(rememberCookie.getValue());
            login.setRememberId(true);
        }

        ModelAndView mv = new ModelAndView("user/login/loginForm");
        return mv;
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView loginSuccess(@Valid Login login, BindingResult bindingResult,
                                     HttpSession session, HttpServletResponse response) throws Exception {

        if(bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("user/login/loginForm");
            return mv;
        }

        try {

            Account account = userService.loginAuth(login);
            session.setAttribute("account", account);

            Cookie rememberCookie = new Cookie("REMEMBER", login.getUserMid());
            rememberCookie.setPath("/");
            if(login.isRememberId()) {
                rememberCookie.setMaxAge(60*60*24*7);
            } else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);

        } catch (IdPasswordNotMatchingException e) {
            bindingResult.rejectValue("pw", "notMatch", "아이디와 비밀번호가 맞지않습니다.");
            ModelAndView mv = new ModelAndView("user/login/loginForm");
            return mv;
        }

        ModelAndView mv = new ModelAndView("login/loginSuccess");
        return mv;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
}
