package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Entity.Login;
import com.example.demo.Repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    // ✅ REGISTER
    @PostMapping("/register")
    public Login register(@RequestBody Login user){

        Login existing = loginRepository.findByEmail(user.getEmail());

       if(existing != null){
            throw new RuntimeException("User not found");
        }
        existing.setName(user.getName());
        existing.setNumber(user.getNumber());
        existing.setPassword(user.getPassword());

        loginRepository.save(existing);

        return loginRepository.save(existing);
    }

    // ✅ LOGIN (EMAIL + PASSWORD CHECK)
    public Login loginuser(String email, String password){

        

        Login user = loginRepository.findByEmail(email);

        if(user != null && user.getPassword().equals(password)){
            return user;
        }

        throw new RuntimeException("Invalid Email or Password");
    }

    // ✅ RESET PASSWORD
    public boolean resetPassword(String email, String newPassword){

        Login user = loginRepository.findByEmail(email);

        if(user != null){
            user.setPassword(newPassword);
            loginRepository.save(user);
            return true;
        }

        return false;
    }

    public Login getUserById(Long id){
        return loginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}