package com.jsell.latte.domain.User.Dto;

import com.jsell.latte.domain.User.Domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    public abstract class UserReqDto {
        protected String name;

        public abstract User toEntity();
    }

    public class CreateUserReqDto extends UserReqDto {
        private String intro;

        @Override
        public User toEntity() {
            return User.builder().name(this.name).intro(this.intro).build();
        }
    }
}
