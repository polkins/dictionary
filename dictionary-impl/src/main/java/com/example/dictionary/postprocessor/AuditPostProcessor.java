package com.example.dictionary.postprocessor;

import com.example.dictionary.annotation.Audit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@AllArgsConstructor
public class AuditPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class type = bean.getClass();

        return getAuditProxy(type, bean);
    }

    @Nullable
    private Object getAuditProxy(Class type, Object bean) {
        var interfacesMethods = getInterfacesMethods(type);
        var classMethods = Arrays.stream(type.getMethods());
        if (Stream.concat(interfacesMethods, classMethods).anyMatch(isAuditMethod())) {
            return createAuditProxy(type, bean);
        }
        return bean;
    }

    private Object createAuditProxy(Class type, Object bean) {
        return Proxy.newProxyInstance(
                type.getClassLoader(),
                type.getInterfaces(),
                (Object proxy, Method method, Object[] args) -> {
                    Optional<Audit> annotation;
                    annotation = ofNullable(getOverriddenMethod(method, type).getDeclaredAnnotation(Audit.class));

                    if (annotation.isEmpty()) {
                        annotation = getInterfacesMethods(proxy.getClass())
                                .filter(getMethodNamePredicate(method))
                                .filter(isAuditMethod())
                                .findAny()
                                .map(m -> AnnotationUtils.getAnnotation(m, Audit.class));
                    }

                    if (annotation.isPresent()) {
                        audit(method, args);
                    }

                    return method.invoke(bean, args);
                });
    }

    private void audit(Method method, Object[] args) {
        var parametersInfo = getParametersInfo(method, args);
        log.info("method name: {}, return type: {}, parameters: ({})", method.getName(), method.getReturnType(), parametersInfo);
    }

    private String getParametersInfo(Method method, Object[] args) {
        var parameters = method.getParameters();

        var length = parameters.length;
        ArrayList<String> parametersInfo = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            var parameterName = parameters[i].getName();
            var parameterType = parameters[i].getType();
            var parameterValue = args[i];

            var parameterInfo = "name: " + parameterName + ", type: " + parameterType + ", value: " + parameterValue;
            parametersInfo.add(parameterInfo);
        }

        return String.join("; ", parametersInfo);
    }

    @NotNull
    private Stream<Method> getInterfacesMethods(Class type) {
        return Arrays.stream(type.getInterfaces()).map(Class::getMethods).flatMap(Arrays::stream);
    }

    Predicate<Method> isAuditMethod() {
        return m -> AnnotationUtils.getAnnotation(m, Audit.class) != null;
    }

    private Method getOverriddenMethod(Method method, Class<?> targetClass) throws NoSuchMethodException {
        return targetClass.getMethod(method.getName(), method.getParameterTypes());
    }

    @NotNull
    private Predicate<Method> getMethodNamePredicate(Method method) {
        return m -> m.getName().equals(method.getName());
    }
}
