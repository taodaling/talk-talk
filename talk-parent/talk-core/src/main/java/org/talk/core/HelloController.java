package org.talk.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/hello")
@RestController
@RefreshScope
public class HelloController {
    @Value("${greet}")
    private String greet;

    @RequestMapping("/date")
    public String getDate() {
        return greet + ":" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
