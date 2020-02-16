package com.gp.beershop.entity;

import com.gp.beershop.security.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
    @OneToMany(mappedBy = "user")
    private Set<OrderEntity> orders;
    private UserRole userRole;
}
