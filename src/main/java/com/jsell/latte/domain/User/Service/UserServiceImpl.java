package com.jsell.latte.domain.User.Service;

import com.jsell.latte.domain.User.Domain.User;
import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Repository.UserRepository;
import com.jsell.latte.global.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(@RequestBody UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        String password = createUserReqDto.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        createUserReqDto.setPassword(hashedPassword);

        return userRepository.save(createUserReqDto.toEntity()).getId() > 0;
    }

    @Override
    public Optional<String> loginUser(UserDto.LoginUserReqDto loginUserReqDto) throws Exception {
        Optional<User> existsUser = userRepository.findByEmail(loginUserReqDto.getEmail());

        if(existsUser.isEmpty()) throw new UserNotFoundException(String.format("Email [%s] is Not found", loginUserReqDto.getEmail()));

        String token = "this is token";
        return Optional.of(token);
    }
}
