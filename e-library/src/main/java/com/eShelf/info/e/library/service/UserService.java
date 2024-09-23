package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.LoginRequestDto;
import com.eShelf.info.e.library.dto.SignUpRequestDto;
import com.eShelf.info.e.library.dto.SignUpResponseDto;

public interface UserService {
    SignUpResponseDto signup(SignUpRequestDto signUpRequestDto);
    boolean login(LoginRequestDto loginRequestDto);
    boolean logout();
}
