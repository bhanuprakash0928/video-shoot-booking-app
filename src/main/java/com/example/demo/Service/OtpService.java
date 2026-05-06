package com.example.demo.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Login;
import com.example.demo.Repository.LoginRepository;

@Service
public class OtpService {

    @Autowired
    private LoginRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    // ✅ SEND OTP
    public String sendOtp(String email, String type){
        Login user = repo.findByEmail(email);

        // 🔴 SIGNUP: If user has a password, they are already fully registered
        if(type.equals("signup") && user != null && user.getPassword() != null){
            return "User already exists ❌";
        }

        // 🔴 FORGOT: user MUST exist
        if(type.equals("forgot") && user == null){
            return "User not found ❌";
        }

        // 🔹 If signup & user not exists → create temp user with ONLY email
        if(user == null){
            user = new Login();
            user.setEmail(email);
        }

        // 🔹 Generate OTP & Set Expiry to 3 mins
        String otp = String.valueOf((int)(Math.random()*900000) + 100000);
        user.setotp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(3));

        repo.save(user); // Now this works because password is nullable!

        // 📧 SEND EMAIL
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + otp + "\nValid for 3 minutes.");
        mailSender.send(message);

        return "OTP sent to email ✅";
    }

    // ✅ VERIFY OTP
    public boolean verifyOtp(String email, String enteredOtp){
        Login user = repo.findByEmail(email);

        if(user == null || user.getotp() == null) return false;

        // ❌ Expired: Clear the OTP from DB after 3 minutes as requested
        if(LocalDateTime.now().isAfter(user.getOtpExpiry())){
            user.setotp(null);
            repo.save(user);
            return false;
        }

        // ❌ Wrong OTP
        if(!user.getotp().equals(enteredOtp)) return false;

        // ✅ Clear OTP after success
        user.setotp(null);
        repo.save(user);

        return true;
    }
}