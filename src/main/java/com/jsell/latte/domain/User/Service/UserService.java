package com.jsell.latte.domain.User.Service;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.global.Common.Dto.Token;

import java.util.Optional;

public interface UserService {
    /**
     * 회원가입을 위한 로직을 수행하는 메소드
     * @param createUserReqDto UserReqDto를 상속받아 email, password, name, intro를 요청에 가지고 있음
     * @return 회원가입 성공 여부 반환
     * @throws Exception
     */
    boolean createUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception;

    /**
     * 로그인을 위한 로직을 수행하는 메서드
     * @param loginUserReqDto UserReqDto를 상속받아 email, password를 요청으로 받음
     * @return 로그인 성공 시 토큰을 반환
     * @throws Exception UserNotFoundException
     */
    Token loginUser(UserDto.LoginUserReqDto loginUserReqDto) throws Exception;

    UserDto.UpdateUserResDto updateUser(UserDto.UpdateUserReqDto updateUserReqDto) throws Exception;
}
