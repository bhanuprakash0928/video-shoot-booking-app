package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Login;
import com.example.demo.Service.LoginService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // ✅ Register
        @PostMapping("/register")
        public Login registeruser(@RequestBody Login user){
            return loginService.register(user);
        }

    // ✅ Login (EMAIL + PASSWORD CHECK)
    @PostMapping("/login")
    public Login login(@RequestBody Login user) {
        return loginService.loginuser(user.getEmail(), user.getPassword());
    }

    // ✅ Reset Password (needed for your JS)
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Login user) {
        boolean updated = loginService.resetPassword(user.getEmail(), user.getPassword());

        return updated ? "Password Updated" : "User Not Found";
    }

    @GetMapping("/user/profile")
        public Login getProfile(@RequestParam Long userId){
            return loginService.getUserById(userId);
        }


        @PostMapping("/user/change-password")
    public String changePassword(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword
    ){

        boolean updated = loginService.changePassword(
                userId,
                currentPassword,
                newPassword
        );

        return updated
                ? "Password Changed Successfully"
                : "Current Password Incorrect";
    }


}