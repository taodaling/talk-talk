package org.talk.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class UserService {
    @Value("${greet}")
    private String greet;

    public String greet(){
        return greet;
    }
}
