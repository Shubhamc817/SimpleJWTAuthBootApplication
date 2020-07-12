package com.test.bootapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class AppUser implements Serializable
{
    @Id
    @Column(name = "USER_NAME", unique = true, nullable = false)
    @JsonProperty(value = "UserName")
    private String userName;

    @Override
    public String toString() {
        return "AppUser{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", career='" + career + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @JsonProperty(value = "FirstName")
    @Column(name="FIRST_NAME")
    private String firstName;

    @JsonProperty(value = "LastName")
    @Column(name="LAST_NAME")
    private String lastName;

    @JsonProperty(value = "Career")
    @Column(name="career")
    private String career;

    @JsonProperty(value = "Password")
    @Column(name="password")
    private String password;

    public AppUser()
    {

    }

    @Transient
    private String result;

    public AppUser(String result)
    {
        this.result = result;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
