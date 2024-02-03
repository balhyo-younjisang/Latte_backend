package com.jsell.latte.domain.User.Service;

import com.jsell.latte.domain.User.Dto.UserDto;

public interface UserService {
    boolean createUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception;
}
