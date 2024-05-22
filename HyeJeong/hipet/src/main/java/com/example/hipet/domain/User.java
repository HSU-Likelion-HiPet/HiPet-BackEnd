package com.example.hipet.domain;

import jakarta.persistence.*;
import lombok.*;
import util.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Long userId; //고유의 아이디 - 사용자에게 부여되는

    @Column(name="LOGIN_ID")
    private String loginId; //로그인 아이디

    @Column(name="PASSWORD")
    private String password; //비밀번호

    @Column(name="USER_NAME")
    private String userName; //사용자 이름

    @Column(name="NICKNAME")
    private String nickname; //닉네임

    @Column(name="ADDRESS")
    private String address; //주소 -> 범위는 미정..

    @Column(name="PHONE_NUMBER")
    private String phoneNumber; //전화번호

    @Column(name="PROFILE_INFO")
    private String profileInfo; //사용자 소개




}
