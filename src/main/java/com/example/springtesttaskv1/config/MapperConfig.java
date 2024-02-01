package com.example.springtesttaskv1.config;

import com.example.springtesttaskv1.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public PersonMapper personMapper (){
        return new PersonMapperImpl();
    }

    @Bean
    public HouseMapper houseMapper (){
        return new HouseMapperImpl();
    }

    @Bean
    public HouseHistoryMapper houseHistoryMapper (){
        return new HouseHistoryMapperImpl();
    }
}
