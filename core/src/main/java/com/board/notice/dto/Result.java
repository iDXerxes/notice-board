package com.board.notice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class Result<T> {

    @JsonProperty("code")
    Integer code;

    @JsonProperty("message")
    String message;

    @JsonProperty("body")
    T body;

    @ConstructorProperties({"code", "message", "body"})
    public Result(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Result(T body, HttpStatus status) {
        this(status.value(), status.getReasonPhrase(), body);
    }

    public Result(T body, HttpStatus status, String message) {
        this(status.value(), message, body);
    }

    public static <T> Result<T> ok(T body) {
        return new Result<>(body, HttpStatus.OK);
    }

    public static Result<String> ok() {
        return new Result<>("Success", HttpStatus.OK);
    }
}
