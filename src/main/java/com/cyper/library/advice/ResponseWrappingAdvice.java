package com.cyper.library.advice;

import com.cyper.library.dto.RespDto;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局返回值处理
 */
@ControllerAdvice(basePackages = "com.cyper.library.controller")
@Slf4j
class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    response.getHeaders().setContentType(new MediaType(MediaType.APPLICATION_JSON));
    if (body instanceof RespDto) {
      return body;
    } else if (body instanceof String) {
      return RespDto.success(body).toJson();
    } else if (body instanceof byte[]) {
      response.getHeaders().setContentType(new MediaType(MediaType.APPLICATION_OCTET_STREAM));
      return body;
    } else {
      return RespDto.success(body);
    }
  }

}
