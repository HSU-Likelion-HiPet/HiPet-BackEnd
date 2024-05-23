package com.example.hipet.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse<T> {
    private int status; //http 응답 상태 코드
    private T data; //응답 데이터 -> 어떤 형태로 값이 반환될지 정해지지 않았으므로
    private String message; //응답 메시지

    //통신 성공
    public static <T> CustomApiResponse<T> createSuccess(int status, T data, String message) {
        return new CustomApiResponse<>(status, data, message);
    }

    //통신 실패
    public static <T> CustomApiResponse<T> createFailWithOut(int status, String message) {
        return new CustomApiResponse<>(status,null,message);
    }
}
