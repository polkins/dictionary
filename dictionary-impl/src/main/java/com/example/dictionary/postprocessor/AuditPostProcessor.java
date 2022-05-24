package com.example.dictionary.postprocessor;

import com.example.dictionary.annotation.Audit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class AuditPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class type = bean.getClass();

        var interfaceMethods = Arrays.stream(type.getInterfaces())
                .map(Class::getMethods)
                .flatMap(Arrays::stream);

        var classMethods = Arrays.stream(type.getMethods());

        var auditMethods = Stream.concat(interfaceMethods, classMethods)
                .filter(isAuditMethod())
                .toList();

        for (Method method : auditMethods) {
            var parameters = Arrays.stream(method.getParameters())
                    .map(p-> "name: " + p.getName() + ", type: " + p.getType())
                    .collect(Collectors.joining("; "));

            log.info("method name: {}, return type: {}, method parameters: ({})", method.getName(), method.getReturnType(), parameters);
        }

        return bean;
    }

    Predicate<Method> isAuditMethod() {
        return m -> AnnotationUtils.getAnnotation(m, Audit.class) != null;
    }
}
