package com.cyper.library.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RespDto {
    private Integer code;
    private String message;
    private Object data;

    public static RespDto success(Object data) {
        return new RespDto(200, "success", data);
    }

    public static  RespDto success() {
        return new RespDto(200, "success", null);
    }

    public static  RespDto success(Integer code, Object data) {
        return new RespDto(code, "success", data);
    }

    public static  RespDto error(Object data) {
        return new RespDto(400, "error", data);
    }

    public static  RespDto error(String message) {
        return new RespDto(400, message, null);
    }

    public static  RespDto notFound(String message) {
        return new RespDto(404, message, null);
    }

    public static  RespDto notFound() {
        return new RespDto(404, "not found", null);
    }

    public static  RespDto unauthorized() {
        return new RespDto(401, "无权限进行此操作", null);
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch(Exception e){
            log.error("resp dto toJSON 出错 ", e);
            return "内部错误";
        }
    }
}