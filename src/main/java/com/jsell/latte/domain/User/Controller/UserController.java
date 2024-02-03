package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/user")
public interface UserController {
    @PostMapping("/join")
    public boolean joinUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception;
}
