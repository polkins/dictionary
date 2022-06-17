package com.example.dictionary.times;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class TimesBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> beanClasses = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (isSupportedClass(beanClass)) {
            beanClasses.put(beanName, beanClass);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanClasses.containsKey(beanName)) {
            return createTimesProxy(bean, beanClasses.get(beanName));
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private boolean isSupportedClass(Class<?> beanClass) {
        return Arrays.stream(beanClass.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(Times.class));
    }

    private Object createTimesProxy(Object bean, Class<?> beanClass) {
        return Proxy.newProxyInstance(
                beanClass.getClassLoader(),
                beanClass.getInterfaces(),
                (Object proxy, Method method, Object[] args) -> {
                    findTimesAnnotation(method, beanClass)
                            .ifPresent(it -> invokeMethodAndRepeat(method, bean, args, it.count()));
                    return bean;
                });
    }

    private Optional<Times> findTimesAnnotation(Method method, Class<?> beanClass) throws NoSuchMethodException {
        if (method.isAnnotationPresent(Times.class)) {
            return Optional.of(method.getAnnotation(Times.class));
        }

        Method overriddenMethod = beanClass.getMethod(method.getName(), method.getParameterTypes());
        if (overriddenMethod.isAnnotationPresent(Times.class)) {
            return Optional.of(overriddenMethod.getAnnotation(Times.class));
        }

        return Optional.empty();
    }

    private void invokeMethodAndRepeat(Method method, Object bean, Object[] args, int count) {
        IntStream.range(0, count).forEach(inx -> {
            try {
                method.invoke(bean, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
