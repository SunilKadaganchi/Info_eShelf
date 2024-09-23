package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.LoginRequestDto;
import com.eShelf.info.e.library.dto.SignUpRequestDto;
import com.eShelf.info.e.library.dto.SignUpResponseDto;
import com.eShelf.info.e.library.mapper.UserDtoMapper;
import com.eShelf.info.e.library.model.User;
import com.eShelf.info.e.library.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        User user = UserDtoMapper.convertSignUpRequestDtoToUser(signUpRequestDto);
        User savedUser = userRepository.save(user);

        return UserDtoMapper.convertUserToSignUpResponseDto(savedUser);
    }

    @Override
    public boolean login(LoginRequestDto loginRequestDto) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
