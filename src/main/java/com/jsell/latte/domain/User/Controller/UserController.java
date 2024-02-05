package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.global.Common.Dto.Response;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/user")
public interface UserController {
    @PostMapping("/join")
    public Response<Boolean> joinUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception;
}
