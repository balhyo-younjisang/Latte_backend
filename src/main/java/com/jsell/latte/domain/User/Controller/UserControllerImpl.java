package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Service.UserServiceImpl;
import com.jsell.latte.global.Common.Dto.Response;
import com.jsell.latte.global.Exception.PasswordNotMatchException;
import com.jsell.latte.global.Exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserServiceImpl userServiceImpl;

    @Override
    public Response<Boolean> joinUser(@Valid @RequestBody UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        try {
            return Response.of(201, "Join success", this.userServiceImpl.createUser(createUserReqDto));
        } catch(IllegalArgumentException e) {
            return Response.of(400, e.getMessage(), false);
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.of(500, "Unknown Error", false);
        }
    }

    @Override
    public Response<String> loginUser(@Valid @RequestBody UserDto.LoginUserReqDto loginUserReqDto) throws Exception {
        try {
            return Response.of(200, "Login success", "Bearer " + this.userServiceImpl.loginUser(loginUserReqDto));
        } catch (UserNotFoundException e) {
            return Response.of(404, e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return Response.of(400, e.getMessage(), null);
        } catch (PasswordNotMatchException e) {
            return Response.of(200, e.getMessage(), null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.of(500, "Unknown Error", null);
        }
    }

    @Override
    public Response<UserDto.UpdateUserResDto> updateUser(@Valid @RequestBody UserDto.UpdateUserReqDto updateUserReqDto) throws Exception {
        try {
            return Response.of(200, "Update success", this.userServiceImpl.updateUser(updateUserReqDto));
        } catch (IllegalArgumentException e) {
            return Response.of(400, e.getMessage(), null);
        } catch (ExpiredJwtException e) {
            return Response.of(403, e.getMessage(), null);
        }
    }
}
