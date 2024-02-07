package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Service.UserServiceImpl;
import com.jsell.latte.global.Common.Dto.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
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

    @Override
    public Response<Optional<String>> loginUser(UserDto.LoginUserReqDto loginUserReqDto) throws Exception {
        try {
            return Response.of(200, "Login success", this.userServiceImpl.loginUser(loginUserReqDto));
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.of(500, "Error", Optional.of("Unknown Error"));
        }
    }
}
