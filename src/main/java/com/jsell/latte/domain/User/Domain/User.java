package com.jsell.latte.domain.User.Domain;

import com.jsell.latte.domain.Company.Domain.Company;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false, unique = true)
    private String email;

//    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public User(String email, String name, String password, String intro) {
        Assert.hasLength(email, "email must not be empty");
        Assert.hasLength(name, "name must not be empty");
        Assert.hasLength(password, "password must not be empty");
        Assert.hasLength(intro, "intro must not be empty");

        this.email = email;
        this.name = name;
        this.password = password;
        this.intro = intro;
    }
}
