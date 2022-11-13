package com.jp.website.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WebsiteUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User hardCodedUser = new User("test", "test", new ArrayList<>());
        if (!hardCodedUser.getUsername().equals(username)) {
            throw new UsernameNotFoundException("Incorrect Credentials.");
        }
        return hardCodedUser;
    }
}

