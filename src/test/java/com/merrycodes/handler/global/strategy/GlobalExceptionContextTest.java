package com.merrycodes.handler.global.strategy;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author MerryCodes
 * @date 2020/6/11 9:54
 */
public class GlobalExceptionContextTest {

    @Test
    public void SingletonTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        GlobalExceptionContext context = GlobalExceptionContext.getInstance();
        Class<GlobalExceptionContext> contextClass = GlobalExceptionContext.class;
        Constructor<GlobalExceptionContext> constructor = contextClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}