package com.jsell.latte.domain.User.Domain;

import com.jsell.latte.domain.Company.Domain.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createTest() {
        User user = User.builder().name("Yun jisang").password("ASDF").intro("My name is Yunjisang").build();

        testEntityManager.persist(user);

        System.out.println(testEntityManager.find(User.class, user.getId()));
        System.out.println("============ TEST ==================");
    }
}