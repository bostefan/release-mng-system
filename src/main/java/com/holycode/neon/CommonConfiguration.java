package com.holycode.neon;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
