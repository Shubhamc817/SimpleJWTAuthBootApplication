package com.test.bootapp.controller;

import com.test.bootapp.service.UserService;
import com.test.bootapp.domain.entities.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class UserController
{
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder )
    {
        this.userService = userService;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    /**
     *
     * @param user
     * takes AppUser as input and save it into Repo, #Doesn't require authentication
     */
    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<?> signUp(@RequestBody AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userService.save(user);
        return ResponseEntity.ok("Successfully signed up");
    }

    /**
     *
     * @param userName
     * accepts userName as input and returns AppUser
     */
    @GetMapping("user/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName)
    {

        AppUser user = userService.findByUserName(userName);
        if(Objects.isNull(user))
        {
            return ResponseEntity.ok("User "+userName +" Not found, Please provide valid userId");
        }
        //Password is confidential, Don't share.
        user.setPassword("Confidential");
        return ResponseEntity.ok(user);
    }



}
