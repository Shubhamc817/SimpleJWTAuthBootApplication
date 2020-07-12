package com.test.boot;

import com.test.bootapp.controller.UserController;
import com.test.bootapp.domain.entities.AppUser;
import com.test.bootapp.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
public class UserControllerTest
{
    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Before
    public void setMockMvc()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ExceptionController())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testFindUser() throws Exception
    {
        AppUser appUser = new AppUser();
        appUser.setPassword("altitest");
        appUser.setUserName("Shubham");

        Mockito.when(userService.findByUserName(any(String.class))).thenReturn(appUser);

        MvcResult response = mockMvc
                .perform(get("/user/Shubham")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andReturn();

        String userResponse = "{\"UserName\":\"Shubham\",\"FirstName\":null,\"LastName\":null,\"Career\":null,\"Password\":\"Confidential\"}";

        Assert.assertEquals(userResponse, response.getResponse().getContentAsString());
    }

    @Test
    public void testFindUserForError() throws Exception
    {

        Mockito.when(userService.findByUserName(any(String.class))).thenReturn(null);

        MvcResult response = mockMvc
                .perform(get("/user/Shubham")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        Assert.assertTrue(response.getResponse().getContentAsString().contains("User Shubham Not found, Please provide valid userId"));
    }
}
