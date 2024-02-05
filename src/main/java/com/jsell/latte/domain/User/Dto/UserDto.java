package com.jsell.latte.domain.User.Dto;

import com.jsell.latte.domain.User.Domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserDto {
    public abstract class UserReqDto {
        protected String name;

        public abstract User toEntity();
    }

    public class CreateUserReqDto extends UserReqDto {
        private String intro;
        private String password;

        @Override
        public User toEntity() {
            return User.builder().name(super.name).intro(this.intro).password(this.password).build();
        }
    }
}
