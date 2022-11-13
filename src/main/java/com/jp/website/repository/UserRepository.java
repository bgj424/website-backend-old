package com.jp.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jp.website.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}