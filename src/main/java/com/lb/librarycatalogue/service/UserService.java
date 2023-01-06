package com.lb.librarycatalogue.service;

import com.lb.librarycatalogue.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String retrieveUserInfo() {
        String username = getUserName();
        return getCardNumber(username);
    }

    public String getUserName() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;

    }

    public String getCardNumber(String userName) {
        return userRepository.findByUsername(userName).get().getCardNumber();
    }

}

