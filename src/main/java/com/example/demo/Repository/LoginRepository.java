package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {
     long count();
    Login findByEmail(String email);
}