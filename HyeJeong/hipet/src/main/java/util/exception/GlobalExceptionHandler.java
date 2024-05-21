package util.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import util.response.CustomApiResponse;

import java.util.stream.Collectors;

@ControllerAdvice //애플리케이션 전체에서 발생하는 예외 처리
public class GlobalExceptionHandler {
    //MethodArgumentNotValidException 발생 시 수행할 동작
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomApiResponse<?>>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //에러 메세지를 얻어온다
        String errorMessage = e.getBindingResult()
                .getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.createFailWithOut(HttpStatus.BAD_REQUEST.value(),errorMessage));
    }

    //DB 작업 중 제약 조건 위반 시 발생하는 예외
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse<?>>
    handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.createFailWithOut(HttpStatus.BAD_REQUEST.value(),errorMessage));
    }

}
