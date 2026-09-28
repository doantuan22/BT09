package com.example.bai_tap_09.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class EncodingConfig {

    @Bean
    public CharacterEncodingFilter utf8CharacterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> utf8CharacterEncodingFilterRegistration(
            CharacterEncodingFilter utf8CharacterEncodingFilter) {
        FilterRegistrationBean<CharacterEncodingFilter> registration =
                new FilterRegistrationBean<>(utf8CharacterEncodingFilter);
        registration.setName("utf8CharacterEncodingFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
