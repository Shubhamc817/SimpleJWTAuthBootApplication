package com.test.bootapp.service;

import com.test.bootapp.domain.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{

    private UserService userService;

    public UserDetailServiceImpl(UserService userService)
    {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = Optional.ofNullable(userService.findByUserName(userName)).orElse(null);

        if(Objects.isNull(user))
            throw new UsernameNotFoundException("User not found");

      return new User(user.getUserName(), user.getPassword(), emptyList());

    }
}
