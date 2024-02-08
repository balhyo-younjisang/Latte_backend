package com.jsell.latte.domain.User.Controller;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.global.Common.Dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/api/v1/user")
public interface UserController {
    /**
     * /api/v1/user/join 로 POST 요청 시 회원가입 성공 여부를 반환
     * @param createUserReqDto email, password, name, intro 를 Request Body에서 매칭하는 DTO
     * @return 회원가입 성공 여부를 반환
     * @throws Exception
     */
    @PostMapping("/join")
    public Response<Boolean> joinUser(@Valid @RequestBody UserDto.CreateUserReqDto createUserReqDto) throws Exception;

    /**
     * /api/v1/user/login 으로 POST 요청 시 로그인 로직 처리 후 토큰을 반환
     * @param loginUserReqDto loginUserReqDto email, password를 Request Body에서 DTO로 매칭
     * @return 토큰 반환
     * @throws Exception UserNotFoundException
     */
    @PostMapping("/login")
    public Response<Boolean> loginUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserDto.LoginUserReqDto loginUserReqDto) throws  Exception;

    /**
     * /api/v1/user/update/introduce 경로로 Patch 요청 시 사용자의 intro를 수정 후 사용자 데이터 반환
     * @param updateUserIntroReqDto
     * @return
     * @throws Exception
     */
    @PatchMapping("/update/introduce")
    public Response<UserDto.UpdateUserResDto> updateUser(@SessionAttribute(name = "userId", required = true) Long userId, @Valid @RequestBody UserDto.UpdateUserReqDto updateUserIntroReqDto) throws Exception;

    /**
     * /api/v1/user/logout 경로로 Delete 요청 시 세션을 삭제
     */
    @DeleteMapping("/logout")
    public Response<Void> logout(HttpServletRequest request);
}
