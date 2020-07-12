package com.test.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bootapp.authhelper.JWTAuthTokenUtil;
import com.test.bootapp.controller.AuthController;
import com.test.bootapp.domain.entities.AppUser;
import com.test.bootapp.service.UserDetailServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.springframework.http.MediaType;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AuthControllerTest
{

    @Mock
    UserDetailServiceImpl userDetailService;

    private MockMvc mockMvc;

    @InjectMocks
    AuthController authController;

    @Mock
    JWTAuthTokenUtil jwtAuthTokenUtil;

    @Spy
    AuthenticationManager authenticationManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setMockMvc()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    public void testAuthForUser() throws Exception
    {
        Mockito.when(userDetailService.loadUserByUsername(any()))
                .thenReturn(new User("shubhamc", "altitest",
                        Collections.singleton(new SimpleGrantedAuthority("Admin"))));

        Mockito.when(jwtAuthTokenUtil.generateToken(any())).thenReturn("auth-token-for-user-shubh");

        AppUser appUser = new AppUser();
        appUser.setPassword("altitest");
        appUser.setUserName("shubhamc");

        String requestJson = new ObjectMapper().writeValueAsString(appUser);

        MvcResult response = mockMvc
                .perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andDo(print())
                .andReturn();

        Assert.assertTrue(response.getResponse().getContentAsString().contains("auth-token-for-user-shubh"));
    }

    @Test
    public void testAuthForUserNotSignedUp() throws Exception
    {
        Mockito.when(userDetailService.loadUserByUsername(any()))
                .thenReturn(null);

        Mockito.when(jwtAuthTokenUtil.generateToken(any())).thenReturn(null);

        AppUser appUser = new AppUser();
        appUser.setPassword("altitest");
        appUser.setUserName("shubhamc");

        String requestJson = new ObjectMapper().writeValueAsString(appUser);

        MvcResult response = mockMvc
                .perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertNotNull(response.getResponse().getContentAsString().contains("Invalid Credentials"));


    }
}
