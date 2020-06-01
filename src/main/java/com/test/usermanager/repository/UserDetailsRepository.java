package com.test.usermanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.usermanager.model.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

	Optional<UserDetails> findByName(String name);

	Optional<UserDetails> findByPhone(String phone);

}
