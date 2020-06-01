package com.test.usermanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.usermanager.model.Userdet;

@Repository
public interface UserdetRepository extends JpaRepository<Userdet, Long> {

	Optional<Userdet> findByName(String name);

	Optional<Userdet> findByPhone(String phone);

}
