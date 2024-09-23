package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.LoginRequestDto;
import com.eShelf.info.e.library.dto.SignUpRequestDto;

public interface UserService {
    boolean signup(SignUpRequestDto signUpRequestDto);
    boolean login(LoginRequestDto loginRequestDto);
    boolean logout();
}
