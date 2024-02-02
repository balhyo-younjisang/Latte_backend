package com.jsell.latte.domain.User.Domain;

import com.jsell.latte.domain.Company.Domain.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void create() {
        User user = User.builder().name("Yun jisang").intro("My Name is Yunjisang").build();
    }
}