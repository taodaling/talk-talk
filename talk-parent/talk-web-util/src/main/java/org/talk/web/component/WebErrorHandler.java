package org.talk.web.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.talk.web.exception.BizException;
import org.talk.web.exception.InvalidTokenException;
import org.talk.web.exception.ResourceNotFoundException;
import org.talk.web.model.WebResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@ControllerAdvice
@Slf4j
public class WebErrorHandler {
    @ExceptionHandler({IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ValidationException.class,
            BindException.class,
            MethodArgumentNotValidException.class,
            ResourceNotFoundException.class
    })
    public WebResult<Void> errorByArgument(Exception e, HttpServletRequest request) {
        return WebResult.newArgumentFail(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object error(Exception e, HttpServletRequest request) {
        log.error("access {} fail", request, e);
        return WebResult.newUnknownFail(e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Object errorByBiz(Exception e, HttpServletRequest request) {
        return WebResult.newBizFail(e.getMessage());
    }


    @ExceptionHandler({InvalidTokenException.class,
            io.jsonwebtoken.ExpiredJwtException.class,
            io.jsonwebtoken.SignatureException.class})
    @ResponseBody
    public Object errorBy(Exception e, HttpServletRequest request) {
        return WebResult.newInvalidToken(e.getMessage());
    }
}
