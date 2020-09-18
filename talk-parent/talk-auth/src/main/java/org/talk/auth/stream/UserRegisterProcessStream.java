package org.talk.auth.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserRegisterProcessStream {
    String CHANNEL = "userRegisterProcess";

    @Input(CHANNEL)
    SubscribableChannel input();

    @Output(CHANNEL)
    MessageChannel output();
}
