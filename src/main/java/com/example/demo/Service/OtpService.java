package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Login;
import com.example.demo.Repository.LoginRepository;

@Service
public class OtpService {

    @Autowired
    private LoginRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    /* ================= SEND OTP ================= */

    public String sendOtp(String email, String type){

        Login user = repo.findByEmail(email);

        // ✅ SIGNUP CHECK
        if(type.equals("signup") && user != null && user.getPassword() != null){
            return "User already exists ❌";
        }

        // ✅ FORGOT CHECK
        if(type.equals("forgot") && user == null){
            return "User not found ❌";
        }

        // ✅ CREATE TEMP USER
        if(user == null){
            user = new Login();
            user.setEmail(email);
        }

        // ✅ GENERATE OTP
        String otp = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        // ✅ SAVE OTP
        user.setotp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(3));

        repo.save(user);

        // ✅ SEND MAIL IN BACKGROUND
        sendEmailAsync(email, otp);

        return "OTP sent successfully ✅";
    }


    /* ================= ASYNC EMAIL ================= */

    @Async
    public void sendEmailAsync(String email, String otp){

        try{

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("OTP Verification");

            message.setText(
                    "Your OTP is: " + otp +
                    "\nValid for 3 minutes."
            );

            mailSender.send(message);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /* ================= VERIFY OTP ================= */

    public boolean verifyOtp(String email, String enteredOtp){

        Login user = repo.findByEmail(email);

        if(user == null || user.getotp() == null){
            return false;
        }

        // ✅ EXPIRED
        if(LocalDateTime.now().isAfter(user.getOtpExpiry())){

            user.setotp(null);

            repo.save(user);

            return false;
        }

        // ✅ WRONG OTP
        if(!user.getotp().equals(enteredOtp)){
            return false;
        }

        // ✅ CLEAR OTP
        user.setotp(null);

        repo.save(user);

        return true;
    }
}