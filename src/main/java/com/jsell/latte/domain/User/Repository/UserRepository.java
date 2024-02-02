package com.jsell.latte.domain.User.Repository;

import com.jsell.latte.domain.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
