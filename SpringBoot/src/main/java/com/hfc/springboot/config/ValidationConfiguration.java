package com.hfc.springboot.config;

import jakarta.validation.Validator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationConfigurationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Created by hfc on 2024/9/17.
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class ValidationConfiguration {

    @Bean
    public Validator validator(MessageSource messageSource, ApplicationContext applicationContext, ObjectProvider<ValidationConfigurationCustomizer> customizers) {
        LocalValidatorFactoryBean validator = ValidationAutoConfiguration.defaultValidator(applicationContext, customizers);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

}
