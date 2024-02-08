package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Domain.User;
import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Service.UserServiceImpl;
import com.jsell.latte.global.Common.Dto.Response;
import com.jsell.latte.global.Exception.PasswordNotMatchException;
import com.jsell.latte.global.Exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

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
    public Response<Boolean> loginUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserDto.LoginUserReqDto loginUserReqDto) throws Exception {
        try {
            User user = this.userServiceImpl.loginUser(loginUserReqDto);

            httpServletRequest.getSession().invalidate(); // 기존 세션 파기
            HttpSession session = httpServletRequest.getSession(true); // Session 생성
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(1800); // Session 30분동안 유지

            return Response.of(200, "Login success", true);
        } catch (UserNotFoundException e) {
            return Response.of(404, e.getMessage(), false);
        } catch (IllegalArgumentException e) {
            return Response.of(400, e.getMessage(), false);
        } catch (PasswordNotMatchException e) {
            return Response.of(200, e.getMessage(), false);
        } catch (Exception e) {
            System.out.println(e.toString());
            return Response.of(500, "Unknown Error", false);
        }
    }

    @Override
    public Response<UserDto.UpdateUserResDto> updateUser(@SessionAttribute(name = "userId", required = true) Long userId, @Valid @RequestBody UserDto.UpdateUserReqDto updateUserReqDto) throws Exception {
        try {
            User user = this.userServiceImpl.updateUser(userId, updateUserReqDto);
            UserDto.UpdateUserResDto updateUserResDto = new UserDto().new UpdateUserResDto(user.getEmail(), user.getName(), user.getIntro());

            return Response.of(200, "Update success", updateUserResDto);
        } catch (IllegalArgumentException e) {
            return Response.of(400, e.getMessage(), null);
        } catch (ExpiredJwtException e) {
            return Response.of(403, e.getMessage(), null);
        }
    }

    @Override
    public Response<Void> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false); // Session 이 없으면 null return
            if (session != null) session.invalidate();

            return Response.of(200, "Logout success", null);
        } catch (Exception e) {
            return Response.of(500, e.getMessage(), null);
        }
    }
}
