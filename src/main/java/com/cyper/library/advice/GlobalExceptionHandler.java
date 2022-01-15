package com.cyper.library.advice;

import com.cyper.library.dto.RespDto;
import com.cyper.library.exception.ServiceFailureException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public RespDto methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException ex) {
    log.info("接口入参错误");
    log.info(ex.getMessage());
    String param = ex.getParameter().getParameterName();
    String value = ex.getValue().toString();
    log.info("参数: " + param);
    log.info("值:   " + value);
    return RespDto.error("参数类型错误, " + param + ": " + value);

  }

  @ExceptionHandler(AccessDeniedException.class)
  public RespDto accessDeniedHandler(AccessDeniedException ex) {
    log.info("访问无权访问的接口");
    return RespDto.unauthorized();
  }

  // @ExceptionHandler(HttpMessageConverter.class)
  // public RespDto<String> httpMessageConverterError

  @ExceptionHandler(Exception.class)
  public RespDto allExceptionHandler(Exception ex) {
    log.error("出错了！");
    log.error(ex.getMessage());
    if (ex.getCause() != null) {
      log.error(ex.getCause().toString());
    }
    ex.printStackTrace();
    return RespDto.error("出错了！");
  }

  @ExceptionHandler(ServiceFailureException.class)
  public RespDto serviceFailureExceptionHandler(ServiceFailureException ex) {
    return new RespDto(ex.getStatusCode(), ex.getMessage(), null);
  }

}
