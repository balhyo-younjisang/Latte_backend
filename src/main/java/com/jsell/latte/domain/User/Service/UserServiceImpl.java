package com.jsell.latte.domain.User.Service;

import com.jsell.latte.domain.User.Dto.UserDto;
import com.jsell.latte.domain.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean createUser(UserDto.CreateUserReqDto createUserReqDto) throws Exception {
        return userRepository.save(createUserReqDto.toEntity()).getId() > 0;
    }
}
