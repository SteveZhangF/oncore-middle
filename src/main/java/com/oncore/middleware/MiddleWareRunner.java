package com.oncore.middleware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan
public class MiddleWareRunner {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
