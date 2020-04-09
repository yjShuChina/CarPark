package com.example.carpark.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println("==>注入"+context+"到ApplicationContextHelper<==");
        ac = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (ac == null) {
            System.out.println("[error]==>ApplicationContext未注入，请检查");
            return null;
        }
        System.out.println("生成====>"+clazz+"类");
        return ac.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        if (ac == null) {
            System.out.println("[error]==>ApplicationContext未注入，请检查");
            return null;
        }
        System.out.println("生成====>"+clazz+"类");
        return ac.getBean(name, clazz);
    }
}
