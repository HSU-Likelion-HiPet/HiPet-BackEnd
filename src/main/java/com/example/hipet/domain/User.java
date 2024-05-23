package com.example.hipet.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Long userId; //고유의 아이디 번호 - 사용자에게 부여되는

    @Column(name="LOGIN_ID")
    private String loginId; //로그인 아이디

    @Column(name="PASSWORD")
    private String password; //비밀번호

    @Column(name="USER_NAME")
    private String userName; //사용자 이름

    @Column(name="ADDRESS")
    private String address; //주소 -> 범위는 미정..

    @Column(name="PROFILE_INFO")
    private String profileInfo; //사용자 소개

    @Column(name="PROFILE_PHOTO")
    private String profilePhoto; //사용자 사진


    // 사용자 이름 수정 메소드
    public void changeUserName(String newUserName) {this.userName = newUserName;}

    //사용자 주소 수정 메소드
    public void changeAddress(String newAddress) {this.address = newAddress;}

    //사용자 소개 수정 메소드
    public void changeProfileInfo(String newProfileInfo) {this.profileInfo = newProfileInfo;}

    //사용자 사진 수정 메소드
    public void changeProfilePhoto(String newProfilePhoto) {this.profilePhoto = newProfilePhoto;}

}
