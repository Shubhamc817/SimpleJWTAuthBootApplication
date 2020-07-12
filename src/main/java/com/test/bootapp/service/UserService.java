package com.test.bootapp.service;

import com.test.bootapp.domain.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends JpaRepository<AppUser, String>
{
    AppUser findByUserName(String userName);
}
