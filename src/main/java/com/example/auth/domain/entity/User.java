package com.example.auth.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "USER_ID")
    private UUID id;
    @Column(name = "USER_EMAIL")
    private String email;
    @Column(name = "USER_NICKNAME")
    private String nickname;
    @Column(name = "USER_BIRTH_DAY")
    private LocalDate birthDay;
    @Column(name = "USER_GENDER")
    private String gender;
}
