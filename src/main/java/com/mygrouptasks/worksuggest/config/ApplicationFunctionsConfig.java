package com.mygrouptasks.worksuggest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationFunctionsConfig {

    @Bean(name="applicationfunctions")
    public ApplicationFunctions ApplicationFunctions() {
        return new ApplicationFunctions();
    }
}
