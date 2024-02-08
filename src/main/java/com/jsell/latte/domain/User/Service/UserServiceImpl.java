package com.jsell.latte.domain.User.Service;

import com.jsell.latte.domain.User.Domain.User;
import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Repository.UserRepository;
import com.jsell.latte.global.Config.JwtProvider;
import com.jsell.latte.global.Exception.PasswordNotMatchException;
import com.jsell.latte.global.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        Assert.hasLength(createUserReqDto.getEmail(), "email must not be empty");
        Assert.hasLength(createUserReqDto.getName(), "name must not be empty");
        Assert.hasLength(createUserReqDto.getPassword(), "password must not be empty");
        Assert.hasLength(createUserReqDto.getIntro(), "intro must not be empty");

        String hashedPassword = passwordEncoder.encode(createUserReqDto.getPassword());
        createUserReqDto.setPassword(hashedPassword);

        return userRepository.save(createUserReqDto.toEntity()).getId() > 0;
    }

    @Override
    public String loginUser(UserDto.LoginUserReqDto loginUserReqDto) throws Exception {
        Assert.hasLength(loginUserReqDto.getEmail(), "email must not be empty");
        Assert.hasLength(loginUserReqDto.getPassword(), "password must not be empty");

        User existsUser = userRepository.findByEmail(loginUserReqDto.getEmail()).orElseThrow(() -> new UserNotFoundException(String.format("Email %s is Not found", loginUserReqDto.getEmail())));

        if(!passwordEncoder.matches(loginUserReqDto.getPassword(), existsUser.getPassword())) {
            throw new PasswordNotMatchException("password do not match");
        }

        String token = jwtProvider.generateJwtToken(existsUser);
        return token;
    }

    @Override
    public UserDto.UpdateUserResDto updateUser(UserDto.UpdateUserReqDto updateUserReqDto) throws Exception {
        Assert.hasLength(updateUserReqDto.getIntro(), "intro must not be empty");
        Assert.hasLength(updateUserReqDto.getName(), "name must not be empty");

        String email = jwtConfig.getEmailFromToken(token);

        User updateUser = this.userRepository.findByEmail(email).orElseThrow();

        updateUser.setIntro(updateUserReqDto.getIntro());
        updateUser.setName(updateUserReqDto.getName());

        this.userRepository.save(updateUser);
        return null;
    }
}
