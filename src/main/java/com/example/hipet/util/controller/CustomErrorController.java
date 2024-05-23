package com.example.hipet.util.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.hipet.util.response.CustomApiResponse;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error") //error 경로로 들어오는 요청을 이 클래스의 메소드로 매핑
    public ResponseEntity<CustomApiResponse<?>> handleError(HttpServletRequest request) {//HttpServletRequest로부터 status code 가져오기
        //ERROR_STATUS_CODE라는 이름의 속성 값을 가져온다 -> Object 타입으로 반환
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // 400 Bad Request
            if(statusCode == 400) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new CustomApiResponse<>
                                (HttpStatus.BAD_REQUEST.value(), null,"잘못된 요청입니다."));
            }

            //403 Forbidden
            else if(statusCode == 403) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(new CustomApiResponse<>
                                (HttpStatus.FORBIDDEN.value(), null,"접근이 금지되었습니다."));
            }

            //404 Not Found
            else if(statusCode==404){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new CustomApiResponse<>
                                (HttpStatus.NOT_FOUND.value(), null,"요청을 찾을 수 없습니다."));

            }

            // 405 Method Not Allowed
            else if(statusCode==405){
                return ResponseEntity
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .body(new CustomApiResponse<>
                                (HttpStatus.METHOD_NOT_ALLOWED.value(), null, "허용되지 않는 메소드입니다."));
            }

            //500 Internal Server Error
            else{
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                null,"내부 서버 오류가 발생했습니다."));
            }
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        null,"내부 서버 오류가 발생했습니다."));


    }

}
