package org.talk.web.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.talk.web.context.WebContext;
import org.talk.web.exception.InvalidTokenException;
import org.talk.web.model.LoginUserModel;
import org.talk.web.safe.JWTParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTParser parser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("access-token");
        if (accessToken != null) {
            WebContext.getInstance().setJwt(accessToken);

            LoginUserModel model = new LoginUserModel();
            Claims claims = parser.parse(accessToken);

            if (!"auth".equals(claims.get("type", String.class))) {
                throw new InvalidTokenException();
            }

            model.setId(claims.get("userid", Integer.class));
            model.setUsername(claims.get("username", String.class));
            WebContext.getInstance().setUser(model);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebContext.clear();
    }
}
