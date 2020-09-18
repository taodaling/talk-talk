package org.talk.web.context;

import lombok.Data;
import lombok.Setter;
import org.talk.web.exception.InvalidTokenException;
import org.talk.web.model.LoginUserModel;

@Setter
public class WebContext {
    private static ThreadLocal<WebContext> threadLocal = ThreadLocal.withInitial(WebContext::new);

    private String jwt;
    private LoginUserModel user;

    public String getJwt() {
        if (jwt == null) {
            throw new InvalidTokenException();
        }
        return jwt;
    }


    public LoginUserModel getUser() {
        if (jwt == null) {
            throw new InvalidTokenException();
        }
        return user;
    }

    public static WebContext getInstance() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
