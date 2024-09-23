package com.eShelf.info.e.library.mapper;

import com.eShelf.info.e.library.dto.SignUpRequestDto;
import com.eShelf.info.e.library.dto.SignUpResponseDto;
import com.eShelf.info.e.library.model.User;

public class UserDtoMapper {
    public static User convertSignUpRequestDtoToUser(SignUpRequestDto signUpRequestDto){
        User user = new User();
        user.setName(signUpRequestDto.getName());
        user.setEmailId(signUpRequestDto.getEmailId());
        user.setPassword(signUpRequestDto.getPassword());
        user.setRole(signUpRequestDto.getRole());
        user.setEmployeeId(signUpRequestDto.getEmployeeId());

        return user;
    }

    public static SignUpResponseDto convertUserToSignUpResponseDto(User user){
        SignUpResponseDto res = new SignUpResponseDto();
        res.setName(user.getName());
        res.setEmail(user.getEmailId());
        res.setEmployeeId(user.getEmployeeId());

        return res;
    }

}
