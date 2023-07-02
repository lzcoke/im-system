package org.yunqi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yunqi.common.config.security.ResponseUtil;
import org.yunqi.common.utils.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

/**
 * 全局异常捕获
 *
 * @author zhengliu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public void handleException(HttpServletResponse response, AccessDeniedException exception) {
        String message = exception.getLocalizedMessage();
        log.error("全局异常捕获AccessDeniedException：{}", message);
        ResponseUtil.out(response, Result.error(401, "暂无权限"));
    }

    @ExceptionHandler(ServiceException.class)
    public void handleException(HttpServletResponse response, ServiceException exception) {
        ResponseUtil.out(response, Result.error(exception.getCode(), exception.getErrorMsg()));
    }


    @ExceptionHandler(NullPointerException.class)
    public void handleException(HttpServletResponse response, NullPointerException exception) {
        ResponseUtil.out(response, Result.error(ErrorEnum.UNKNOWN.getCode(), ErrorEnum.UNKNOWN.getMsg()));
    }
}