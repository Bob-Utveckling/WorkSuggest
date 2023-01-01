package com.mygrouptasks.worksuggest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagesConfig {

    @Bean(name="messageseng")
    public MessagesEng messagesEng() {
        return new MessagesEng();
    }
}
