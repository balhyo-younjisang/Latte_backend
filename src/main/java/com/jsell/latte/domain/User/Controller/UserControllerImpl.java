package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController{
    private final UserServiceImpl userServiceImpl;
    @Override
    public boolean joinUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        return this.userServiceImpl.createUser(createUserReqDto);
    }
}
