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
    @Column(name="LOGIN_ID")
    private String loginId; //로그인 아이디

    @Column(name="PASSWORD")
    private String password; //비밀번호

    @Column(name="PHONE_NUMBER")
    private String phoneNumber; //전화번호

    @Column(name="ADDRESS")
    private String address; //주소 -> 범위는 미정..

    @Column(name="NICKNAME")
    private String nickname; //닉네임

    //"loginId" : "pet",
    //    "password" : "1234",
    //    "phoneNumber" : "01012345678",
    //    "address" : "서울"
    //    "nickname" : "하이펫"

}
