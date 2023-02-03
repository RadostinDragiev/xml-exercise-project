package com.example.xmlexercise.services;

import com.example.xmlexercise.models.dtos.UserAddDto;

import java.util.List;

public interface UserService {
    void addUser(UserAddDto userAddDto);

    UserAddDto getRandomUser();

    List<UserAddDto> getAllUsers();
}
