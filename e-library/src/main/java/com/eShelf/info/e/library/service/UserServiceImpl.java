package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.LoginRequestDto;
import com.eShelf.info.e.library.dto.SignUpRequestDto;
import com.eShelf.info.e.library.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean signup(SignUpRequestDto signUpRequestDto) {
        return false;
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
