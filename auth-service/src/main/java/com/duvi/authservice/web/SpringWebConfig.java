package com.duvi.authservice.web;

import com.duvi.authservice.web.convertor.DateFormatter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private MessageSource messageSource;

    public SpringWebConfig() {
        super();
    }
    public SpringWebConfig(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }
    @Override

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

//    Static resources, i18n texts and formatters

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        WebMvcConfigurer.super.addResourceHandlers(resourceHandlerRegistry);
        resourceHandlerRegistry.addResourceHandler("/").addResourceLocations("/", "classpath:/META-INF/resources");
        resourceHandlerRegistry.addResourceHandler("/images/**").addResourceLocations("/images/");
        resourceHandlerRegistry.addResourceHandler("/css/**").addResourceLocations("/css/");
        resourceHandlerRegistry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

    @Bean
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        WebMvcConfigurer.super.addFormatters(formatterRegistry);
        formatterRegistry.addFormatter(dateFormatter());
    }


    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter(messageSource);
    }

//    Thymeleaf artifacts
    @Bean
    SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/src/main/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine =  new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }


}
