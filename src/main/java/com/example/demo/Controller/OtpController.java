package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin("*")
public class OtpController {

    @Autowired
    private OtpService otpService;

    // ✅ SEND OTP (signup + forgot)
   @PostMapping("/send")
    public String sendOtp(@RequestParam String email,
                        @RequestParam String type) {
        try {
            return otpService.sendOtp(email, type);
        } catch(Exception e){
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    // ✅ VERIFY OTP
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(email, otp);

        return valid ? "OTP Verified ✅" : "Invalid or Expired OTP ❌";
    }
}