package com.example.demo.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author:james
 * day:2020-09-13
 */
@Controller
public class HomeController {
    @RequestMapping(value = {"/"})
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/denied")
    public String denied(Model model) {
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "denied";
    }

    @RequestMapping(value = "/user")
    public String user(Model model) {
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "user";
    }

    @RequestMapping(value = "/admin")
    public String admin(Model model) {
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "admin";
    }
    @RequestMapping(value = "/manage")
    public String v(Model model) {
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "manage";
    }

    @RequestMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "home";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id) {
//        roleResourceService.delete(id);
        return "ok";
    }
}
