package com.example.xmlexercise.services.impl;

import com.example.xmlexercise.models.dtos.UserAddDto;
import com.example.xmlexercise.models.entities.User;
import com.example.xmlexercise.repositories.UserRepository;
import com.example.xmlexercise.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addUser(UserAddDto userAddDto) {
        User user = this.modelMapper.map(userAddDto, User.class);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserAddDto getRandomUser() {
        long id = new Random().nextInt((int) this.userRepository.count()) + 1;
        Optional<User> byId = this.userRepository.findById(id);
        return this.modelMapper.map(byId, UserAddDto.class);
    }

    @Override
    public List<UserAddDto> getAllUsers() {
        return Arrays.stream(this.modelMapper.map(this.userRepository.findAll(), UserAddDto[].class)).collect(Collectors.toList());
    }
}
