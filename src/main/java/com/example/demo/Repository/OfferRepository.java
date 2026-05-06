package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entity.Offers;

public interface  OfferRepository extends JpaRepository<Offers, Long> {
	
}
