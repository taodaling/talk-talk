package org.talk.web.model;

import lombok.Data;
import org.talk.web.exception.BizException;
import org.talk.web.exception.InvalidTokenException;

@Data
public class WebResult<T> {
    private int code;
    private String msg;
    private T body;

    public static int SUCCESS_CODE = 0;
    public static int UNKNOWN_ERROR = -1;
    public static int BIZ_ERROR = 1;
    public static int INVALID_ARGUMENT = 2;
    public static int INVALID_TOKEN = 3;

    public WebResult<T> validate() {
        if (code == UNKNOWN_ERROR) {
            throw new RuntimeException(msg);
        }
        if (code == BIZ_ERROR) {
            throw new BizException(msg);
        }
        if (code == INVALID_ARGUMENT) {
            throw new IllegalArgumentException(msg);
        }
        if (code == INVALID_TOKEN) {
            throw new InvalidTokenException(msg);
        }
        return this;
    }

    public static <T> WebResult<T> newSuccess(T body) {
        return of(0, null, body);
    }

    public static <T> WebResult<T> newSuccess() {
        return newSuccess(null);
    }

    public static <T> WebResult<T> newFail(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> WebResult<T> newUnknownFail(String msg) {
        return newFail(UNKNOWN_ERROR, msg);
    }

    public static <T> WebResult<T> newBizFail(String msg) {
        return newFail(BIZ_ERROR, msg);
    }

    public static <T> WebResult<T> newArgumentFail(String msg) {
        return newFail(INVALID_ARGUMENT, msg);
    }

    public static <T> WebResult<T> newInvalidToken(String msg) {
        return newFail(INVALID_TOKEN, msg);
    }

    public static <T> WebResult<T> of(int code, String msg, T body) {
        WebResult<T> result = new WebResult<>();
        result.body = body;
        result.code = code;
        result.msg = msg;
        return result;
    }
}
