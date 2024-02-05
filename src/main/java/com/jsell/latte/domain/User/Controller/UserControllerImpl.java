package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Service.UserServiceImpl;
import com.jsell.latte.global.Common.Dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController {
    private final UserServiceImpl userServiceImpl;

    @Override
    public Response<Boolean> joinUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        try {
            return Response.of(200, "Join success", this.userServiceImpl.createUser(createUserReqDto));
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.of(500, "Error", false);
        }
    }
}
