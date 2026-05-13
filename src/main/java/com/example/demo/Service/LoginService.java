package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Login;
import com.example.demo.Repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

   
  public Login register(Login user){

    Login existing = loginRepository.findByEmail(user.getEmail());

  
    if(existing != null){

        existing.setName(user.getName());
        existing.setNumber(user.getNumber());
        existing.setPassword(user.getPassword());

        return loginRepository.save(existing);
    }


    return loginRepository.save(user);
}


    public Login loginuser(String email, String password){

        Login user = loginRepository.findByEmail(email);

        if(user != null && user.getPassword().equals(password)){
            return user;
        }

        throw new RuntimeException("Invalid Email or Password");
    }

  
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
    public Login updateProfile(Login updatedUser){

        Login existingUser =
                loginRepository.findById(updatedUser.getId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setNumber(updatedUser.getNumber());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setProfileImage(updatedUser.getProfileImage());
        existingUser.setDarkMode(updatedUser.getDarkMode());

        return loginRepository.save(existingUser);
    }
    public boolean changePassword(
            Long userId,
            String currentPassword,
            String newPassword
    ){

        Login user = loginRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if(user.getPassword().equals(currentPassword)){

            user.setPassword(newPassword);

            loginRepository.save(user);

            return true;
        }

        return false;
    }
}