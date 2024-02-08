package com.jsell.latte.domain.User.Dto;

import com.jsell.latte.domain.User.Domain.User;
import lombok.*;

public class UserDto {
    @Getter
    public abstract class UserReqDto {
        protected String email;

        public UserReqDto(String email) {
            this.email = email;
        }

        public abstract User toEntity();
    }

    @Getter
    public abstract class UserResDto {
        protected String email;

        public UserResDto(String email) {this.email = email;}
    }

    @Getter
    public class CreateUserReqDto extends UserReqDto {
        private String name;
        private String intro;
        private String password;

        public void setPassword(String password) {
            this.password = password;
        }

        public CreateUserReqDto(String name, String email, String intro, String password) {
            super(email);
            this.name = name;
            this.intro = intro;
            this.password = password;
        }

        @Override
        public User toEntity() {
            return User.builder().email(email).name(this.name).intro(this.intro).password(this.password).build();
        }
    }

    @Getter
    public class LoginUserReqDto extends UserReqDto {
        private String password;

        public LoginUserReqDto(String email, String password) {
            super(email);
            this.password = password;
        }

        @Override
        public User toEntity() {
            return User.builder().email(email).password(password).build();
        }
    }

    /**
     * @Todo 필요한 경우 UserReqDto 상속
     */
    @Getter
    public class UpdateUserReqDto extends UserReqDto {
        private String intro;
        private String name;

        public UpdateUserReqDto(String email, String name, String intro) {
            super(email);
            this.name = name;
            this.intro = intro;
        }

        @Override
        public User toEntity() {
            return null;
        }
    }

    @Getter
    public class UpdateUserResDto extends UserResDto {
        private String name;
        private String intro;

        public UpdateUserResDto(String email, String name, String intro) {
            super(email);
            this.intro = intro;
            this.name = name;
        }
    }
}
